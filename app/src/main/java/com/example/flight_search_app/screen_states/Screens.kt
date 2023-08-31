package com.example.flight_search_app.screen_states

import androidx.navigation.NamedNavArgument
import com.example.flight_search_app.R
import com.example.flight_search_app.nav.BottomNavItems

val AIRLINE_ID = "airlineId"

interface routes{
    val route: String
}

val BOTTOM_NAV_ITEMS = listOf(
    BottomNavItems(
        title = "Home",
        route = Screens.HomeScreen.route,
        icon = R.drawable.home,
    ),
    BottomNavItems(
        title = "Favorites",
        route = Screens.FavoriteScreen.route,
        icon  = R.drawable.favorite,
    )
)


sealed class Screens(): routes{



    object HomeScreen: Screens(){
        override val route: String
            get() = "HomeScreen"
    }
    object SearchScreen: Screens() {
        override val route: String
            get() = "SearchScreen"
    }

    object FavoriteScreen: Screens(){
        override val route: String
            get() = "FavoriteScreen"

    }
    object DetailScreen: Screens(){
        override val route: String
            get() = "DetailScreen/{$AIRLINE_ID}"
    }

    fun buildRoute(routes: routes, argument: String? = null):String {
     return   if (argument != null && routes.route.contains("{argumentId}")){
            route.replace("{argumentId}" ,argument)
        }else
            route
    }
}


interface NavigationCommands{
    val arguments: List<NamedNavArgument>
    val destination: String
}

object SearchScreenDirections {

}