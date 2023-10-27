package com.monzo.androidtest.api.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoritesDao {

    @Query("SELECT * FROM articles ORDER BY published DESC")
    fun getFavorites(): LiveData<List<Article>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(favorites: Article)

    @Query("DELETE FROM articles where id=:id")
    fun delete(id: String)

    @Query("SELECT * FROM articles where id=:inputId")
    fun getFavorite(inputId: String): LiveData<List<Article>>

}