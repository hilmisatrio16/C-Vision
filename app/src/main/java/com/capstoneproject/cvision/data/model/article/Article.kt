package com.capstoneproject.cvision.data.model.article

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    var image: Int = 0,
    var title: String? = null,
    var content: String? = null,
    var imageUrl: String? = null,
    var urlArt: String? = null
) : Serializable
