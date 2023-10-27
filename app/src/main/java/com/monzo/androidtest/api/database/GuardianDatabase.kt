package com.monzo.androidtest.api.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Article::class], version = 1)
abstract class GuardianDatabase() : RoomDatabase() {

    abstract fun favoritesDAO(): FavoritesDao

    companion object {
        @Volatile
        private var guardianDatabase: GuardianDatabase? = null

        fun getInstance(context: Context): GuardianDatabase {
            if(guardianDatabase == null) guardianDatabase = synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    GuardianDatabase::class.java,
                    "favorites_db"
                ).build()
            }
            return guardianDatabase as GuardianDatabase
        }
    }
}