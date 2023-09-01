package com.example.flight_search_app.ui.theme.screens.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flight_search_app.R
import com.example.flight_search_app.components.FlightSearchBar
import com.example.flight_search_app.getApplication

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    savedStateHandle: SavedStateHandle,
    navigateBack: () -> Unit = {},
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
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = "Destinations",
                                textAlign = TextAlign.Center,
                                modifier = Modifier.fillMaxWidth(),
                            )
                        },
                        navigationIcon = {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                    navigateBack()
                                }
                            )
                        },
                        actions = {
                            val icon = if (state.isStared)
                                R.drawable.star_icon_selected
                            else
                                R.drawable.star_icon


                            Icon(
                                painter = painterResource(
                                    id = icon
                                ),
                                contentDescription = "",
                                modifier = Modifier.clickable {
                                  vm.onStarTapped()
                                }
                            )
                        }
                    )
                },
                modifier = Modifier.fillMaxSize()
            ) { paddingValues ->
                Box(
                    modifier = Modifier
                        .padding(paddingValues)
                        .fillMaxSize(),
                ) {
                    FlightSearchBar(
                        modifier = Modifier.align(
                            Alignment.TopCenter,
                        ),
                        searchResults = state.queryResults,
                        onQueryChange = vm::searchFlights,
                        onFlightSelect = vm::onFlightSelect,
                    )

                    Column(
                        verticalArrangement = Arrangement.Top,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .padding(
                                top = paddingValues.calculateTopPadding() + 20.dp,
                            )
                            .fillMaxSize()
                    ) {

                        Divider()

                        Spacer(
                            modifier = Modifier.size(50.dp),
                        )

                        Text(text = "Name:  ${state.flightFrom.name}")
                        Text(text = "IATA Code:  ${state.flightFrom.iataCode}")
                        Text(text = "PassengerCount:  ${state.flightFrom.passengers}")

                        Spacer(
                            modifier = Modifier.size(20.dp),
                        )

                        Text(
                            text = "TO:",
                            fontWeight = FontWeight.Bold,
                        )

                        Spacer(
                            modifier = Modifier.size(20.dp),
                        )


                        Text(text = "Name:  ${state.flightTo?.name ?: ""}")
                        Text(text = "IATA Code:  ${state.flightTo?.iataCode ?: ""}")
                        Text(text = "PassengerCount:  ${state.flightTo?.passengers ?: ""}")

                        Spacer(
                            modifier = Modifier.size(60.dp),
                        )

                        if(state.isStared){
                            OutlinedButton(
                                onClick = {
                                    vm.onConfirmFlight(state.flightTo)
                                }
                            ) {
                                Text(text = "Confirm Flight")
                            }
                        }
                    }
                }
            }
        }
    }
}