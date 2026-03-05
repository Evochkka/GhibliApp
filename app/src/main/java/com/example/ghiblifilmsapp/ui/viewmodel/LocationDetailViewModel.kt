package com.example.ghiblifilmsapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilmsapp.data.repository.LocationRepository
import com.example.ghiblifilmsapp.domain.model.LocationDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationDetailViewModel @Inject constructor(
    private val repository: LocationRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val locationId: String = checkNotNull(savedStateHandle["locationId"])

    data class LocationDetailState(
        val isLoading: Boolean = false,
        val locationDetail: LocationDetail? = null,
        val errorMessage: String? = null
    )

    private val _state = MutableStateFlow(LocationDetailState())
    val state: StateFlow<LocationDetailState> = _state.asStateFlow()

    init {
        loadLocationDetail()
    }

    private fun loadLocationDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val detail = repository.getLocationDetail(locationId)
                _state.update { it.copy(isLoading = false, locationDetail = detail) }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error loading location: ${e.message}"
                    )
                }
            }
        }
    }

    fun retry() {
        loadLocationDetail()
    }
}