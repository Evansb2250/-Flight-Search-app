package com.example.flight_search_app.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.flight_search_app.SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopAppBar(
    enableFocusChangeEvent: Boolean = true,
    searchAirports: (airport: String) -> Unit = {},
    navigateToSearchScreen: () -> Unit = {}
){

    TopAppBar(
        title = {
            SearchBar(
                enableFocusChangeEvent = enableFocusChangeEvent,
                navigateToSearchBar = navigateToSearchScreen,
                searchAirports = searchAirports
            )
        },
    )
}