package com.example.flight_search_app.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FlightCard(
    id: String,
    onClickEvent:(id: String) -> Unit = {},
    content: @Composable RowScope.() -> Unit = {},
){
    Card(
        modifier = Modifier
            .height(100.dp)
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp,
            )
            .clickable {
                onClickEvent(id)
            },
        shape = RoundedCornerShape(10)
    ) {
        Row(
            modifier = Modifier
                .padding(
                    horizontal = 16.dp,
                )
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            content()
        }
    }
}