package com.example.ghiblifilmsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilmsapp.data.repository.PeopleRepository
import com.example.ghiblifilmsapp.domain.model.People
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
class PeopleListViewModel @Inject constructor(
    private val repository: PeopleRepository
) : ViewModel() {

    data class PeopleListState(
        val isLoading: Boolean = false,
        val allPeoples: List<People> = emptyList(),
        val currentPage: Int = 0,
        val pageSize: Int = 20,
        val errorMessage: String? = null
    )

    private val _state = MutableStateFlow(PeopleListState())
    val state: StateFlow<PeopleListState> = _state.asStateFlow()

    val pagedPeoples = _state.map { state ->
        val fromIndex = state.currentPage * state.pageSize
        val toIndex = minOf(fromIndex + state.pageSize, state.allPeoples.size)

        if (state.allPeoples.isEmpty() || fromIndex >= state.allPeoples.size) {
            emptyList<People>()
        } else {
            state.allPeoples.subList(fromIndex, toIndex)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init { loadPeoples() }

    fun loadPeoples() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            try {
                val peoples = repository.getPeoples()
                _state.update { it.copy(isLoading = false, allPeoples = peoples) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, errorMessage = e.message) }
            }
        }
    }

    fun changePage(page: Int) {
        _state.update { it.copy(currentPage = page) }
    }
}