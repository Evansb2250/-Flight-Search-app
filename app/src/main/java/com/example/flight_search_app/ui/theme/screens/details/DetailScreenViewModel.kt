package com.example.flight_search_app.ui.theme.screens.details

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.flight_search_app.database.models.Airport
import com.example.flight_search_app.repo.FlightRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


sealed class UIState {
    object Loading : UIState()
    object Error : UIState()
    data class Success(val data: Airport) : UIState()
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
        Log.d("DEBUGLOADSCREEN", " My id ${airPlaneId}")
        viewModelScope.launch {
            Log.d("DEBUGLOADSCREEN", " My id ${airPlaneId}")

            val result = flightRepository.getAirLineDetails(airPlaneId)
            _state.value = if (result != null) {
                UIState.Success(result)
            } else (
                    UIState.Error
                    )
        }
    }
}
