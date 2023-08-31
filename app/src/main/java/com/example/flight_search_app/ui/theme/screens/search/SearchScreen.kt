package com.example.flight_search_app.ui.theme.screens.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flight_search_app.components.CustomTopAppBar
import com.example.flight_search_app.components.FlightCard
import com.example.flight_search_app.viewModelProvider

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    vm: SearchScreenViewModel = viewModel(
        factory = viewModelProvider
    ),
    navigateDetailsScreen: (id: String) -> Unit = {}
) {
    val state = vm.state.collectAsState().value


    Scaffold(
        topBar = {
            CustomTopAppBar(
                enableFocusChangeEvent = false,
                searchAirports = { charracters ->
                    vm.updateSearch(charracters)
                }
            )
        },
        bottomBar = {

        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier.padding(paddingValues)
        ) {
            when (state) {
                SearchScreenUiState.Error -> {
                    item {
                        Text(text = "Error")
                    }
                }

                SearchScreenUiState.Loading -> {
                    item {
                        Text(text = "Loading")
                    }
                }

                is SearchScreenUiState.Success -> {
                    items(items = state.data, key = { it } ) { item ->
                        FlightCard(
                            id = item.name,
                            onClickEvent = navigateDetailsScreen,
                            content = {
                                Column(
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                ) {
                                    Text(
                                        text = item.iataCode,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    Text(text = item.name)
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}