package com.example.flight_search_app.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class Favorite(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("departure_code")
    val deprecated: String = "",
    @ColumnInfo("destination_code")
    val destinationCode: String,
)
