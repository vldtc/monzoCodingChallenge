package com.monzo.androidtest.api.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "articles")
data class Article(
    @PrimaryKey(autoGenerate = false) val id: String,
    val thumbnail: String,
    val sectionId: String,
    val sectionName: String,
    val published: String,
    val title: String,
    val url: String
)
