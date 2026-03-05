package com.example.ghiblifilmsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilmsapp.domain.model.Film
import com.example.ghiblifilmsapp.data.repository.FilmRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FilmListViewModel @Inject constructor(
    private val repository: FilmRepository
) : ViewModel() {

    data class FilmListState(
        val isLoading: Boolean = false,
        val allFilms: List<Film> = emptyList(),
        val currentPage: Int = 0,
        val pageSize: Int = 20,
        val errorMessage: String? = null
    )

    private val _state = MutableStateFlow(FilmListState())
    val state: StateFlow<FilmListState> = _state.asStateFlow()

    // Список конкретно для текущей страницы
    val pagedFilms = _state.map { state ->
        val fromIndex = state.currentPage * state.pageSize
        val toIndex = minOf(fromIndex + state.pageSize, state.allFilms.size)

        if (state.allFilms.isEmpty() || fromIndex >= state.allFilms.size) {
            emptyList<Film>()
        } else {
            state.allFilms.subList(fromIndex, toIndex)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init { loadFilms() }

    fun loadFilms() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val films = repository.getFilms()
                _state.update { it.copy(isLoading = false, allFilms = films) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun changePage(page: Int) {
        _state.update { it.copy(currentPage = page) }
    }
}