package com.capstoneproject.cvision.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstoneproject.cvision.data.repository.ArticleRepository
import com.capstoneproject.cvision.data.repository.AuthenticationRepository

class HomeViewModel(
    private val authenticationRepository: AuthenticationRepository,
    private val articleRepository: ArticleRepository
) : ViewModel() {

    fun getNameUser(): LiveData<String>{
        return authenticationRepository.nameUser.asLiveData()
    }

    fun getArticles() = articleRepository.getArticles()
}