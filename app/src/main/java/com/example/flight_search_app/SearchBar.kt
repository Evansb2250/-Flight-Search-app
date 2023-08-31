package com.example.flight_search_app

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusEvent
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import com.example.flight_search_app.ui.theme.SidePadding


data class SearchBarState(
    private val initialText: String = "",
    private val initialIsFocused: Boolean,
    private val initialNavigateState: Boolean = false,
    private val initialEnableNavState: Boolean,
) {
    var text by mutableStateOf(initialText)
    var isFocused by mutableStateOf(initialIsFocused)
    val enableNavigation by mutableStateOf(initialEnableNavState)
    var navigateToDetailScreen by mutableStateOf(initialNavigateState)

    companion object {
        val SearchBarSaver = mapSaver(
            save = { searchBarState: SearchBarState ->
                mapOf(
                    "initialText" to searchBarState.text,
                    "initailIsFocused" to searchBarState.isFocused,
                    "initialNavigateState" to searchBarState.navigateToDetailScreen,
                    "initialEnableNavState" to searchBarState.enableNavigation,
                )
            },
            restore = { restorationMap: Map<String, Any?> ->
                SearchBarState(
                    initialText = restorationMap["initialText"] as String,
                    initialIsFocused = restorationMap["initailIsFocused"] as Boolean,
                    initialEnableNavState = restorationMap["initialEnableNavState"] as Boolean,
                )
            },
        )
    }

}

@Composable
fun SearchBarStateRememberSeavable(
    enableNavigation: Boolean,
) = rememberSaveable(
    saver = SearchBarState.SearchBarSaver,
) {
    SearchBarState("", false, initialEnableNavState = enableNavigation)
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    enableFocusChangeEvent: Boolean = true,
    searchBarState: SearchBarState = SearchBarStateRememberSeavable(enableFocusChangeEvent),
    searchAirports: (airport: String) -> Unit = {},
    navigateToSearchBar: () -> Unit = {},
) {
    LaunchedEffect(key1 = searchBarState.navigateToDetailScreen) {
        if (searchBarState.navigateToDetailScreen && searchBarState.navigateToDetailScreen)
            navigateToSearchBar()
    }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    //Don't show the keyboard the initial time the the user interacts with
    //with the search bar.
    if (searchBarState.enableNavigation) {
        keyboardController?.hide()
    }
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Top
    ) {
        OutlinedTextField(
            value = searchBarState.text,
            onValueChange = { newText ->
                searchBarState.text = newText
                searchAirports(searchBarState.text)
            },
            modifier = Modifier
                .padding(
                    horizontal = 8.dp,
                    vertical = 20.dp,
                )
                .size(
                    width = 300.dp,
                    height = 50.dp
                )
                .onFocusEvent { focusState ->
                    searchBarState.isFocused = focusState.isFocused
                    if (searchBarState.isFocused) {
                        searchBarState.navigateToDetailScreen = true
                    }
                },
            textStyle = TextStyle(
                textAlign = TextAlign.Justify,
                baselineShift = BaselineShift(2f),
            ),
            maxLines = 1,
            singleLine = true,
            shape = RoundedCornerShape(20.dp)
        )
    }
}
