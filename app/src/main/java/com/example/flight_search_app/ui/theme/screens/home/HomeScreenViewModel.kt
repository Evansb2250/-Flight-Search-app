package com.example.flight_search_app.ui.theme.screens.home

import android.app.appsearch.AppSearchResult
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flight_search_app.database.models.Airport
import com.example.flight_search_app.repo.FavoritesRepository
import com.example.flight_search_app.repo.FlightRepository
import com.example.flight_search_app.ui.theme.screens.search.SearchScreenUiState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

sealed class HomeScreenUiState {
    object Loading : HomeScreenUiState()
    object Error : HomeScreenUiState()
    data class Success(
        val data: List<Airport> = emptyList(),
        val searchResults: List<Airport> = emptyList()
    ) :
        HomeScreenUiState()
}


class HomeScreenViewModel(
    val airportRepository: FlightRepository,
) : ViewModel() {

    private var searchState = MutableStateFlow<List<Airport>>(emptyList())

    private val currentFlights: Flow<List<Airport>> = airportRepository
        .observeAll()
        .map { list ->
            list
    }

    val state = currentFlights.combine(
        flow = searchState,
    )
    { currentFlights, searchState, ->
        Log.d(
            "DEBUG COMBINE",
            "CurrentFlight ${currentFlights.size} \n" +
                    "SearchState ${searchState.size}"

        )
        if (currentFlights.isEmpty()) {
            HomeScreenUiState.Error
        } else
            HomeScreenUiState.Success(
                data = currentFlights.subList(0, 10),
                searchResults = searchState,
            )
    }


    fun updateSearch(text: String) {
        viewModelScope.launch {
            if (text.isEmpty()){
                searchState.value = emptyList()
            }else{
                val result = airportRepository.getAirlineDetailsWith( "${text}%")
                searchState.value = result
            }
        }
    }
}
