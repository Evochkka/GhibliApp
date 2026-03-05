package com.example.ghiblifilmsapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilmsapp.data.repository.FilmRepository
import com.example.ghiblifilmsapp.domain.model.FilmDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmDetailViewModel @Inject constructor(
    private val repository: FilmRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val filmId: String = checkNotNull(savedStateHandle["filmId"])

    data class FilmDetailState(
        val isLoading: Boolean = false,
        val filmDetail: FilmDetail? = null,
        val errorMessage: String? = null
    )

    private val _state = MutableStateFlow(FilmDetailState())
    val state: StateFlow<FilmDetailState> = _state.asStateFlow()

    init {
        loadFilmDetail()
    }

    private fun loadFilmDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val detail = repository.getFilmDetail(filmId)
                _state.update { it.copy(isLoading = false, filmDetail = detail) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Ошибка загрузки деталей: ${e.message}"
                    )
                }
            }
        }
    }

    fun retry() {
        loadFilmDetail()
    }
}