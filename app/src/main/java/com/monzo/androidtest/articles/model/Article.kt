package com.monzo.androidtest.articles.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

data class Article(
 val id: String,
 val thumbnail: String,
 val sectionId: String,
 val sectionName: String,
 val published: Date,
 val title: String,
 val url: String
)
