package com.example.flight_search_app.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "Airport",
)
data class Airport(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("iata_code")
    val iataCode: String,
    val name: String,
    val passengers: Int,
)
