package com.example.ghiblifilmsapp.ui.screen.Location

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
import com.example.ghiblifilmsapp.R
import com.example.ghiblifilmsapp.domain.model.Location
import com.example.ghiblifilmsapp.ui.viewmodel.LocationListViewModel
import com.example.ghiblifilmsapp.ui.screen.Film.PaginationControls

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationListScreen(
    navController: NavController,
    viewModel: LocationListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentItems by viewModel.pagedLocations.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "VariantCode: GHIBLI-FILMS-MOD_E21_DEEP_LINK",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelMedium
        )
        TopAppBar(
            title = { Text("Locations") },
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
            } else if (state.error != null) {
                com.example.ghiblifilmsapp.ui.screen.Film.ErrorMessage(
                    message = state.error ?: "Unknown Error",
                    onRetry = { viewModel.loadLocations() }
                )
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(currentItems) { location ->
                        LocationItem(
                            location = location,
                            onClick = { navController.navigate("detail/${location.id}") }
                        )
                    }
                }
            }
        }

        PaginationControls(
            currentPage = state.currentPage,
            totalItems = state.allLocations.size,
            pageSize = state.pageSize,
            onPageChange = { viewModel.changePage(it) }
        )
    }
}

@Composable
fun LocationItem(location: Location, onClick: () -> Unit) {
    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Surface(
                modifier = Modifier.fillMaxWidth().height(100.dp),
                color = MaterialTheme.colorScheme.secondaryContainer,
                shape = MaterialTheme.shapes.small
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(
                        painter = painterResource(R.drawable.typo_location),
                        contentDescription = "Gender Icon",
                        modifier = Modifier.size(60.dp)
                    )
                }
            }

            Text(
                text = location.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                maxLines = 2,
                minLines = 2
            )

            Text(
                text = "Terrain: ${location.terrain}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 1
            )
        }
    }
}