package com.example.flight_search_app.ui.theme.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flight_search_app.database.models.Airport
import com.example.flight_search_app.repo.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class SearchScreenUiState {
    object Loading : SearchScreenUiState()
    object Error : SearchScreenUiState()
    data class Success(val data: List<Airport>) : SearchScreenUiState()
}


class SearchScreenViewModel(
    val airportRepository: FlightRepository,
) : ViewModel() {
    private val _state: MutableStateFlow<SearchScreenUiState> =
        MutableStateFlow(SearchScreenUiState.Success(emptyList()))
    val state = _state.asStateFlow()

    fun updateSearch(characters: String) {
        viewModelScope.launch {
            _state.value = SearchScreenUiState.Loading

            val result: List<Airport> = airportRepository.getAirlineDetailsWith("$characters%")

            _state.update {
                if (result.isEmpty()) {
                    SearchScreenUiState.Error
                } else {
                    SearchScreenUiState.Success(result)
                }
            }
        }
    }
}