package com.capstoneproject.cvision.ui.article

import androidx.lifecycle.ViewModel
import com.capstoneproject.cvision.data.repository.ArticleRepository

class ArticleViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    fun getArticles() = articleRepository.getArticles()

}