package com.example.flight_search_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.flight_search_app.database.dao.AirportDAO
import com.example.flight_search_app.database.models.Airport
import com.example.flight_search_app.database.models.Favorite

@Database(version = 1, entities = arrayOf(Favorite::class, Airport::class))
abstract class FlightSearchDatabase : RoomDatabase() {

    abstract fun airportDao(): AirportDAO


    companion object {
        private var INSTANCE: FlightSearchDatabase? = null

        fun getDatabase(
            context: Context
        ): FlightSearchDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    FlightSearchDatabase::class.java,
                    "Flight_database"
                )
                    .createFromAsset("database/flight_search.db")
                    .build()

                INSTANCE = instance
                instance
            }

        }
    }
}