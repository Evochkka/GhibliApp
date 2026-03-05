package com.example.ghiblifilmsapp.ui.screen.People

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.ghiblifilmsapp.domain.model.People
import com.example.ghiblifilmsapp.ui.viewmodel.PeopleListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PeopleListScreen(
    navController: NavController,
    viewModel: PeopleListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val currentItems by viewModel.pagedPeoples.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = "VariantCode: GHIBLI-FILMS-MOD_E21_DEEP_LINK",
            modifier = Modifier.padding(16.dp),
            style = MaterialTheme.typography.labelMedium
        )
        TopAppBar(
            title = { Text("Peoples") },
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
                    items(currentItems) { people ->
                        PeopleItem(people = people, onClick = { navController.navigate("detail/${people.id}") })
                    }
                }
            }
        }

        PaginationControls(
            currentPage = state.currentPage,
            totalItems = state.allPeoples.size,
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
fun PeopleItem(people: People, onClick: () -> Unit) {

    Card(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = people.name,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp),
                minLines = 3, maxLines = 3
            )

            Text(
                text = people.gender,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
    }
}
