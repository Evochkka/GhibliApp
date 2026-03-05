package com.example.ghiblifilmsapp.ui.screen.Film

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.ghiblifilmsapp.R
import com.example.ghiblifilmsapp.ui.viewmodel.FilmDetailViewModel

@Composable
fun FilmDetailScreen(
    viewModel: FilmDetailViewModel = hiltViewModel()
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
            state.filmDetail != null -> {
                FilmDetailContent(state.filmDetail!!)
            }
        }
    }
}


@Composable
fun FilmDetailContent(
    detail: com.example.ghiblifilmsapp.domain.model.FilmDetail
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp, 48.dp)
    ) {
        if (!detail.bannerUrl.isNullOrEmpty()) {
            AsyncImage(
                model = detail.bannerUrl,
                contentDescription = null,
                placeholder = painterResource(R.drawable.loading),
                error = painterResource(R.drawable.warning),
                modifier = Modifier.fillMaxWidth()
            )
        }
        detail.title?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
        Text(
            text = "Director: ${detail.director}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Producer: ${detail.producer}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Release date: ${detail.releaseDate}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Rotten Tomatoes: ${detail.rtScore}",
            style = MaterialTheme.typography.titleMedium
        )
        detail.description?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}