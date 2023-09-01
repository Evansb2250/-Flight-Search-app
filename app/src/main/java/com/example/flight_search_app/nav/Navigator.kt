package com.example.flight_search_app.nav

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.magnifier
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.flight_search_app.R
import com.example.flight_search_app.screen_states.AIRLINE_ID
import com.example.flight_search_app.screen_states.BOTTOM_NAV_ITEMS
import com.example.flight_search_app.screen_states.Screens
import com.example.flight_search_app.ui.theme.screens.details.DetailScreen
import com.example.flight_search_app.ui.theme.screens.home.HomeScreen
import com.example.flight_search_app.ui.theme.screens.search.SearchScreen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FlightApp() {
    val navController = rememberNavController()
    val currentBackStackDestination =
        navController.currentBackStackEntryAsState().value?.destination?.route
            ?: Screens.HomeScreen.route

    Scaffold(
        bottomBar = {
            if (currentBackStackDestination != Screens.DetailScreen.route)
                NavigationBar {
                    BOTTOM_NAV_ITEMS.forEach { item ->
                        NavigationBarItem(
                            modifier = Modifier.wrapContentHeight(),
                            selected = item.route == currentBackStackDestination,
                            label = {
                                Text(text = item.title)
                            },
                            onClick = {
                                navController.navigate(item.route) {
                                    popUpTo(item.route) {
                                        inclusive = true

                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    painter = painterResource(
                                        id = item.icon,
                                    ),
                                    contentDescription = "",
                                )
                            }
                        )
                    }

                }
        }

    ) { padding ->

        NavHost(
            modifier = Modifier.padding(padding),
            navController = navController,
            startDestination = Screens.HomeScreen.route
        ) {
            composable(Screens.HomeScreen.route) {

                HomeScreen(
                    navigateToDetailsScreen = { id ->
                        navController.navigate(
                            "DetailScreen/$id"
                        )
                    },
                )
            }

            composable(
                Screens.SearchScreen.route,
            ) {
                SearchScreen(
                    navigateDetailsScreen = { id ->
                        navController.navigate(
                            "DetailScreen/$id"
                        )
                    }
                )
            }

            composable(
                route = Screens.FavoriteScreen.route
            ) {
                Text(text = "Favourite Screen")
            }

            composable(
                Screens.DetailScreen.route,
                arguments = listOf(navArgument(AIRLINE_ID) { type = NavType.StringType })
            ) { navBackStackEntry ->

                val airportId = navBackStackEntry.arguments?.getString(AIRLINE_ID)
                    ?: "Unknown"

                val savedStateInstance = SavedStateHandle().apply {
                    set("userId", airportId)
                }
                Text(text = airportId)

                DetailScreen(
                    savedStateHandle = savedStateInstance,
                    navigateBack = navController::popBackStack
                )
            }
        }
    }
}
