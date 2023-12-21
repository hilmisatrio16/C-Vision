package com.capstoneproject.cvision.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.capstoneproject.cvision.data.model.article.Article

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article")
    fun getArticles(): LiveData<List<Article>>

    @Insert
    fun insertArticles(vararg article: Article)
}