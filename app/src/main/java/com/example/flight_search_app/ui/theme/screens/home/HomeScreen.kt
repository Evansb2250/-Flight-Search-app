package com.example.flight_search_app.ui.theme.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flight_search_app.components.FlightCard
import com.example.flight_search_app.components.FlightSearchBar
import com.example.flight_search_app.viewModelProvider


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    vm: HomeScreenViewModel = viewModel(
        factory = viewModelProvider,
    ),
    navigateToDetailsScreen: (id: String) -> Unit = {},
) {
    val state = vm.state.collectAsState(initial = HomeScreenUiState.Loading).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "FlightApp",
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
            )
        }
    ) { padding ->
        when (state) {
            HomeScreenUiState.Error -> {
                Text(text = "Error")
            }

            HomeScreenUiState.Loading -> {
                Text(text = "Loading")
            }

            is HomeScreenUiState.Success -> {

                Box(
                    Modifier
                        .padding(padding)
                        .semantics { isTraversalGroup = true }
                ) {

                    FlightSearchBar(
                        modifier = Modifier
                            .align(Alignment.TopCenter),
                        onQueryChange = vm::updateSearch,
                        searchResults = state.searchResults,
                        onFlightSelect = navigateToDetailsScreen,
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(top = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                            .fillMaxWidth()
                            .padding(
                                top = padding.calculateTopPadding() + 50.dp,
                                start = 16.dp,
                                bottom = 16.dp,
                            )
                    ) {
                        stickyHeader {
                            Divider()
                        }
                        items(
                            items = state.data,
                            key = { airline -> airline.id },
                        ) { airline ->
                            FlightCard(
                                id = airline.id.toString(),
                                onClickEvent = navigateToDetailsScreen,
                                content = {
                                    Text(
                                        text = "${airline.iataCode}",
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier.weight(.5f)
                                    )
                                    Text(
                                        text = "${airline.name}",
                                        modifier = Modifier.weight(2f)
                                    )
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}