package com.example.flight_search_app

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.flight_search_app.ui.theme.screens.details.DetailScreenViewModel
import com.example.flight_search_app.ui.theme.screens.home.HomeScreenViewModel
import com.example.flight_search_app.ui.theme.screens.search.SearchScreenViewModel

val viewModelProvider :ViewModelProvider.Factory= viewModelFactory {
    initializer {
        val flightRepository = this.getApplication().container.flightRepository
        SearchScreenViewModel(flightRepository)
    }
    initializer {
        val flightRepository = this.getApplication().container.flightRepository
        HomeScreenViewModel(flightRepository)
    }

}

fun CreationExtras.getApplication(): FlightSearchApplication{
    return this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as FlightSearchApplication
}