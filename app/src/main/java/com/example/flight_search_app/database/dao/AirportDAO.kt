package com.example.flight_search_app.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.flight_search_app.database.models.Airport
import kotlinx.coroutines.flow.Flow

@Dao
interface AirportDAO {

    @Insert
    suspend fun insertAirport(airport: Airport)

    @Query("SELECT * FROM Airport")
    fun getAirports(): Flow<List<Airport>>

    @Query("SELECT * FROM Airport WHERE id=:airPlaneId")
    suspend fun getAirline(airPlaneId: Int): Airport?

    @Query("Select * FROM Airport WHERE name like :s")
    suspend fun getAirlineDetailsWith(s: String): List<Airport>

}