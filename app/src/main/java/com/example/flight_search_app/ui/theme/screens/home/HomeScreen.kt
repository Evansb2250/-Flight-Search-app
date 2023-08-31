package com.example.flight_search_app.ui.theme.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.flight_search_app.components.FlightCard
import com.example.flight_search_app.viewModelProvider


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    vm: HomeScreenViewModel = viewModel(
        factory = viewModelProvider,
    ),
    backStackEntry: String,
    navigateToDetailsScreen: (id: String) -> Unit = {},
    navigateToSearchScreen: () -> Unit = {}
) {
    val state = vm.state.collectAsState(initial = HomeScreenUiState.Loading).value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = "FlightApp")
                },
            )
        },
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
                    var text by remember {
                        mutableStateOf("")
                    }
                    var isActive by remember {
                        mutableStateOf(false)
                    }
                    SearchBar(
                        modifier = Modifier
                            .align(Alignment.TopCenter)
                            .semantics {
                                traversalIndex = -1f
                            }
                            .padding(
                                horizontal = 16.dp,
                            ),
                        query = text,
                        onSearch = {},
                        onQueryChange = {
                            text = it
                            vm.updateSearch(text)
                        },
                        active = isActive,
                        onActiveChange = { isActive = it },
                        content = {
                            LazyColumn {
                                items(
                                    items = state.searchResults,
                                    key = { airline -> airline.id }
                                ) { item ->
                                    Divider()

                                    ListItem(
                                        modifier = Modifier.clickable {
                                            navigateToDetailsScreen(item.id.toString())
                                        },
                                        headlineContent = {
                                            Text(text = item.name)
                                        },
                                    )

                                    Divider()
                                }
                            }
                        }
                    )

                    LazyColumn(
                        contentPadding = PaddingValues(top = 20.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .fillMaxWidth()
                            .padding(padding)
                    ) {

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
