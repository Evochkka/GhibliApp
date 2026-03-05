package com.example.ghiblifilmsapp.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ghiblifilmsapp.data.repository.PeopleRepository
import com.example.ghiblifilmsapp.domain.model.PeopleDetail
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PeopleDetailViewModel @Inject constructor(
    private val repository: PeopleRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val peopleId: String = checkNotNull(savedStateHandle["peopleId"])

    data class PeopleDetailState(
        val isLoading: Boolean = false,
        val peopleDetail: PeopleDetail? = null,
        val errorMessage: String? = null
    )

    private val _state = MutableStateFlow(PeopleDetailState())
    val state: StateFlow<PeopleDetailState> = _state.asStateFlow()

    init {
        loadPeopleDetail()
    }

    private fun loadPeopleDetail() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }
            try {
                val detail = repository.getPeopleDetail(peopleId)
                _state.update { it.copy(isLoading = false, peopleDetail = detail) }
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
        loadPeopleDetail()
    }
}