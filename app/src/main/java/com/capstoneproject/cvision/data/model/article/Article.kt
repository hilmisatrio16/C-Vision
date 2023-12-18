package com.capstoneproject.cvision.data.model.article

import java.io.Serializable

data class Article(
    val image: Int,
    val title: String,
    val content: String
): Serializable
