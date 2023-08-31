package com.example.flight_search_app.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.flight_search_app.database.models.Favorite

@Dao
interface FavoritesDAO {

    @Query("Select * From favorite")
    suspend fun getFavorites(): List<Favorite>
}