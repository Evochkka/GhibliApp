package com.example.ghiblifilmsapp.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController


@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "VariantCode: GHIBLI-FILMS-MOD_E21_DEEP_LINK",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelMedium
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Ghibli",
                style = MaterialTheme.typography.headlineLarge,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            MenuButton(
                text = "Films",
                onClick = { navController.navigate("film_list") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "People",
                onClick = { navController.navigate("people_list") }
            )

            Spacer(modifier = Modifier.height(16.dp))

            MenuButton(
                text = "Locations",
                onClick = { navController.navigate("location_list") }
            )
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit, enabled: Boolean = true) {
    Button(
        onClick = onClick,
        enabled = enabled,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
    ) {
        Text(text = text, style = MaterialTheme.typography.titleMedium)
    }
}