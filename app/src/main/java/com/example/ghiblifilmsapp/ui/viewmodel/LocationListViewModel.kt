package com.example.ghiblifilmsapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilmsapp.data.repository.LocationRepository
import com.example.ghiblifilmsapp.domain.model.Location
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationListViewModel @Inject constructor(
    private val repository: LocationRepository
) : ViewModel() {

    data class LocationListState(
        val isLoading: Boolean = false,
        val allLocations: List<Location> = emptyList(),
        val currentPage: Int = 0,
        val pageSize: Int = 20,
        val error: String? = null
    )

    private val _state = MutableStateFlow(LocationListState())
    val state: StateFlow<LocationListState> = _state.asStateFlow()

    val pagedLocations = _state.map { state ->
        val fromIndex = state.currentPage * state.pageSize
        val toIndex = minOf(fromIndex + state.pageSize, state.allLocations.size)

        if (state.allLocations.isEmpty() || fromIndex >= state.allLocations.size) {
            emptyList()
        } else {
            state.allLocations.subList(fromIndex, toIndex)
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    init {
        loadLocations()
    }

    fun loadLocations() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }
            try {
                val locations = repository.getLocations()
                _state.update { it.copy(isLoading = false, allLocations = locations) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    fun changePage(page: Int) {
        _state.update { it.copy(currentPage = page) }
    }
}