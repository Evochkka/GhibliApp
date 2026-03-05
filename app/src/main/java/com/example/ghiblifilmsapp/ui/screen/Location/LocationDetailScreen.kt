package com.example.ghiblifilmsapp.ui.screen.Location

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ghiblifilmsapp.R
import com.example.ghiblifilmsapp.domain.model.LocationDetail
import com.example.ghiblifilmsapp.ui.screen.Film.ErrorMessage
import com.example.ghiblifilmsapp.ui.viewmodel.LocationDetailViewModel

@Composable
fun LocationDetailScreen(
    viewModel: LocationDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.errorMessage != null -> {
                ErrorMessage(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.retry() }
                )
            }
            state.locationDetail != null -> {
                LocationDetailContent(state.locationDetail!!)
            }
        }
    }
}

@Composable
fun LocationDetailContent(detail: LocationDetail) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, MaterialTheme.shapes.medium),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.typo_location),
                contentDescription = "Gender Icon",
                modifier = Modifier.size(80.dp)
            )
        }

        detail.name?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))
        Text (
            text = "Climate: ${detail.climate}",
            style = MaterialTheme.typography.titleMedium,
        )
        Text (
            text = "Terrain: ${detail.terrain}",
            style = MaterialTheme.typography.titleMedium,
        )
        Text (
            text = "Surface Water: ${detail.surfaceWater}",
            style = MaterialTheme.typography.titleMedium,
        )
    }
}