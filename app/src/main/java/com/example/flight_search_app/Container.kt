package com.example.flight_search_app

import android.content.Context
import com.example.flight_search_app.database.FlightSearchDatabase
import com.example.flight_search_app.repo.FavoritesRepository
import com.example.flight_search_app.repo.FavoritesRepositoryImpl
import com.example.flight_search_app.repo.FlightRepository
import com.example.flight_search_app.repo.FlightRepositoryImpl

interface Container {
    val favoritesRepository: FavoritesRepository
    val flightRepository: FlightRepository
}


class ContainerImpl(context: Context) : Container {

    override val favoritesRepository: FavoritesRepository by lazy {
        FavoritesRepositoryImpl()
    }


    override val flightRepository: FlightRepository by lazy {
        FlightRepositoryImpl(FlightSearchDatabase.getDatabase(context).airportDao())
    }

}