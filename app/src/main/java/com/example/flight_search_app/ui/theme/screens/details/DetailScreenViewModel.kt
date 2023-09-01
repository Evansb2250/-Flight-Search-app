package com.example.flight_search_app.ui.theme.screens.details

import android.provider.Contacts.Intents.UI
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flight_search_app.database.models.Airport
import com.example.flight_search_app.repo.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    data class Success(
        val flightFrom: Airport,
        val flightTo: Airport? = null,
        val queryResults: List<Airport> = emptyList(),
        val isStared: Boolean = false,
    ) : UIState()

}


class DetailScreenViewModel(
    val airPlaneId: String,
    val flightRepository: FlightRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<UIState> = MutableStateFlow(UIState.Loading)
    val state = _state.asStateFlow()

    init {
        loadDetails()
    }

    private fun loadDetails() {
        viewModelScope.launch {
            val result = flightRepository.getAirLineDetails(airPlaneId)
            _state.value = if (result != null) {
                UIState.Success(result)
            } else (UIState.Error)
        }
    }

    fun searchFlights(query: String) {
        viewModelScope.launch {
            val results = flightRepository.getAirlineDetailsWith("$query%")
            _state.update { currentState ->
                if (currentState is UIState.Success) currentState.copy(
                    queryResults = results,
                )
                else UIState.Error
            }
        }
    }

    fun onStarTapped() {
        _state.update { currentState ->
            if (currentState is UIState.Success && currentState.flightTo != null) {
                currentState.copy(
                    isStared = !currentState.isStared,
                )
            } else
                currentState
        }
    }


    fun onConfirmFlight(airport: Airport?){
        viewModelScope.launch {

        }
    }

    fun onFlightSelect(id: String) {
        viewModelScope.launch {
            val flightResult = flightRepository.getAirLineDetails(id)

            _state.update { currentState ->
                if (flightResult != null) {
                    if (currentState is UIState.Success) {
                        currentState.copy(
                            flightTo = flightResult,
                            isStared = false,
                        )
                    } else {
                        UIState.Error
                    }
                } else {
                    UIState.Error
                }
            }
        }
    }
}
