package com.capstoneproject.cvision.data.repository

import androidx.lifecycle.LiveData
import com.capstoneproject.cvision.data.model.article.Article
import com.capstoneproject.cvision.data.room.ArticleDao

class ArticleRepository(
    private val articleDao: ArticleDao
) {

    fun getArticles(): LiveData<List<Article>> {
        return articleDao.getArticles()
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
            articleDao: ArticleDao
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(articleDao)
            }.also { instance = it }
    }
}