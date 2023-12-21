package com.capstoneproject.cvision.ui.home

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.cvision.data.repository.ArticleRepository
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.di.Injection

class HomeViewModelFactory private constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val articleRepository: ArticleRepository
) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(authenticationRepository, articleRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(context: Context): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(
                    Injection.provideAuthRepository(context),
                    Injection.provideArticleRepository(context)
                )
            }.also { instance = it }
    }


}