package com.example.flight_search_app.nav

import androidx.navigation.NavController
import androidx.navigation.ui.navigateUp


data class FlightAppDestination(
    val route: String,
)


class FlightAppNavigationAction (val navigationController: NavController){
    fun navigateTo(destination: FlightAppDestination){
        navigationController.navigate(destination.route)
    }
}