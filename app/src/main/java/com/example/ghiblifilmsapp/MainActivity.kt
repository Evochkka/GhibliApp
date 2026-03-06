package com.example.ghiblifilmsapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.example.ghiblifilmsapp.di.NetworkModule.provideGhibliApi
import com.example.ghiblifilmsapp.di.NetworkModule.provideOkHttpClient
import com.example.ghiblifilmsapp.di.NetworkModule.provideRetrofit
import com.example.ghiblifilmsapp.ui.screen.Film.FilmDetailScreen
import com.example.ghiblifilmsapp.ui.screen.Film.FilmListScreen
import com.example.ghiblifilmsapp.ui.screen.HomeScreen
import com.example.ghiblifilmsapp.ui.screen.Location.LocationDetailScreen
import com.example.ghiblifilmsapp.ui.screen.Location.LocationListScreen
import com.example.ghiblifilmsapp.ui.screen.People.PeopleDetailScreen
import com.example.ghiblifilmsapp.ui.screen.People.PeopleListScreen
import com.example.ghiblifilmsapp.ui.theme.GhibliFilmsAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GhibliFilmsAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home_screen",
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None }
    ) {
        composable("home_screen") { HomeScreen(navController) }
        composable("film_list") { FilmListScreen(navController) }
        composable("people_list") { PeopleListScreen(navController) }

        composable("location_list") { LocationListScreen(navController) }

        composable(
            route = "film_detail/{filmId}",
            arguments = listOf(navArgument("filmId") { type = NavType.StringType })
        ) { FilmDetailScreen() }

        composable(
            route = "people_detail/{peopleId}",
            arguments = listOf(navArgument("peopleId") { type = NavType.StringType })
        ) { PeopleDetailScreen() }

        composable(
            route = "location_detail/{locationId}",
            arguments = listOf(navArgument("locationId") { type = NavType.StringType })
        ) { LocationDetailScreen() }

        composable(
            route = "detail/{id}",
            arguments = listOf(navArgument("id") { type = NavType.StringType }),
            deepLinks = listOf(
                navDeepLink { uriPattern = "app://detail/{id}" }
            )
        ) { backStackEntry ->
            val id = backStackEntry.arguments?.getString("id") ?: ""
            UniversalDetailRouter(id = id, navController = navController)
        }
    }
}

@Composable
fun UniversalDetailRouter(
    id: String,
    navController: NavController
) {
    val api = remember { provideGhibliApi(provideRetrofit(provideOkHttpClient())) }

    LaunchedEffect(id) {
        val previousScreen = navController.previousBackStackEntry?.destination?.route

        val route = when (previousScreen) {
            "film_list" -> "film_detail/$id"
            "people_list" -> "people_detail/$id"
            "location_list" -> "location_detail/$id"
            else -> {
                coroutineScope {
                    val isFilm = async { try { api.getFilm(id).isSuccessful } catch (e: Exception) { false } }
                    val isPerson = async { try { api.getPeople(id).isSuccessful } catch (e: Exception) { false } }
                    val isLocation = async { try { api.getLocation(id).isSuccessful } catch (e: Exception) { false } }

                    when {
                        isFilm.await() -> "film_detail/$id"
                        isPerson.await() -> "people_detail/$id"
                        isLocation.await() -> "location_detail/$id"
                        else -> "home_screen"
                    }
                }
            }
        }

        navController.navigate(route) {
            popUpTo("detail/{id}") { inclusive = true }
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}