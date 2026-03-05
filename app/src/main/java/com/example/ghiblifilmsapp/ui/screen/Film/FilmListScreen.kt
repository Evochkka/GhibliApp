package com.example.ghiblifilmsapp.ui.screen.Film

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ghiblifilmsapp.R
import com.example.ghiblifilmsapp.domain.model.Film
import com.example.ghiblifilmsapp.ui.viewmodel.FilmListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilmListScreen(
    navController: NavController,
    viewModel: FilmListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentItems by viewModel.pagedFilms.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "VariantCode: GHIBLI-FILMS-MOD_E21_DEEP_LINK",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelMedium
        )
        TopAppBar(
            title = { Text("Films") },
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                }
            }
        )
        Box(modifier = Modifier.weight(1f)) {
            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(currentItems) { film ->
                        FilmItem(film = film, onClick = { navController.navigate("detail/${film.id}") })
                    }
                }
            }
        }

        PaginationControls(
            currentPage = state.currentPage,
            totalItems = state.allFilms.size,
            pageSize = state.pageSize,
            onPageChange = { viewModel.changePage(it) }
        )
    }
}

@Composable
fun PaginationControls(
    currentPage: Int,
    totalItems: Int,
    pageSize: Int,
    onPageChange: (Int) -> Unit
) {
    val totalPages = kotlin.math.ceil(totalItems.toDouble() / pageSize).toInt()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(
            onClick = { onPageChange(currentPage - 1) },
            enabled = currentPage > 0
        ) {
            Text("Назад")
        }

        Text(
            text = "Страница ${currentPage + 1} из $totalPages",
            style = MaterialTheme.typography.bodyMedium
        )

        Button(
            onClick = { onPageChange(currentPage + 1) },
            enabled = (currentPage + 1) < totalPages
        ) {
            Text("Вперед")
        }
    }
}

@Composable
fun FilmItem(film: Film, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            if (!film.imageUrl.isNullOrEmpty()) {
                AsyncImage(
                    model = film.imageUrl,
                    placeholder = painterResource(R.drawable.loading),
                    error = painterResource(R.drawable.warning),
                    contentDescription = film.title,
                    modifier = Modifier.fillMaxSize()
                )
            }
            Text(
                text = film.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 3,
                minLines = 3
            )
            Text(
                text = film.releaseDate,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
            Text(
                text = film.runningTime + "min.",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}

@Composable
fun ErrorMessage(message: String, onRetry: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = message, color = MaterialTheme.colorScheme.error)
        Button(onClick = onRetry) {
            Text("Повторить")
        }
    }
}