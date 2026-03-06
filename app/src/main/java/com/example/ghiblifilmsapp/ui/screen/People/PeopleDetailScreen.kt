package com.example.ghiblifilmsapp.ui.screen.People

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.ghiblifilmsapp.R
import com.example.ghiblifilmsapp.ui.viewmodel.PeopleDetailViewModel

@Composable
fun PeopleDetailScreen(
    viewModel: PeopleDetailViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            state.errorMessage != null -> {
                com.example.ghiblifilmsapp.ui.screen.Film.ErrorMessage(
                    message = state.errorMessage!!,
                    onRetry = { viewModel.retry() }
                )
            }
            state.peopleDetail != null -> {
                PeopleDetailContent(state.peopleDetail!!)
            }
        }
    }
}

@Composable
fun PeopleDetailContent(detail: com.example.ghiblifilmsapp.domain.model.PeopleDetail) {

    val genderIcon = when (detail.gender.lowercase()) {
        "male" -> R.drawable.male
        "female" -> R.drawable.female
        else -> R.drawable.warning
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            text = detail.name,
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(top = 16.dp)
        )
        Icon(
            painter = painterResource(id = genderIcon),
            contentDescription = "Gender Icon",
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "Gender: ${detail.gender}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Age: ${detail.age}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Eye color:  ${detail.eyeColor}",
            style = MaterialTheme.typography.titleMedium
        )
        Text(
            text = "Hair color: ${detail.hairColor}",
            style = MaterialTheme.typography.titleMedium
        )
    }
}