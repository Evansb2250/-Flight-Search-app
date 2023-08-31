package com.example.flight_search_app.repo

import com.example.flight_search_app.database.dao.AirportDAO
import com.example.flight_search_app.database.models.Airport
import kotlinx.coroutines.flow.Flow

interface FlightRepository {
    fun observeAll(): Flow<List<Airport>>
    suspend fun getAirLineDetails(airPlaneId: String): Airport?
    suspend fun getAirlineDetailsWith(s: String): List<Airport>
}

class FlightRepositoryImpl(
   val airportDAO: AirportDAO,
): FlightRepository {

   override fun observeAll(): Flow<List<Airport>>{
        return airportDAO.getAirports()
    }

    override suspend fun getAirLineDetails(airPlaneId: String): Airport? {
        return airportDAO.getAirline(airPlaneId.toInt())
    }

    override suspend fun getAirlineDetailsWith(s: String): List<Airport> {
        return airportDAO.getAirlineDetailsWith(s)
    }
}