package com.example.flight_search_app.ui.theme.screens.details

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flight_search_app.getApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    savedStateHandle: SavedStateHandle,
    vm: DetailScreenViewModel = viewModel(
        initializer = {
            val airportId = savedStateHandle.get<String>("userId") ?: ""
            DetailScreenViewModel(
                airportId,
                this.getApplication().container.flightRepository
            )
        }
    )
) {
    val state = vm.state.collectAsStateWithLifecycle().value

    when (state) {
        is UIState.Error -> {
            Text(text = "Error")
        }

        UIState.Loading -> {
                Text(text = "Loading")
        }

        is UIState.Success -> {

            Scaffold(
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->

                LazyColumn() {
                    item {
                        Row() {
                            Text(text = "${state.data.name}")
                        }
                    }

                    item {
                        Text(text = "${state.data.iataCode}")
                    }
                }
            }

        }
    }
}