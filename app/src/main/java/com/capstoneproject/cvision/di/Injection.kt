package com.capstoneproject.cvision.di

import android.content.Context
import com.capstoneproject.cvision.data.dsprefs.AuthPreferences
import com.capstoneproject.cvision.data.dsprefs.dataStore
import com.capstoneproject.cvision.data.remote.retrofit.ApiConfig
import com.capstoneproject.cvision.data.repository.ArticleRepository
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.data.repository.PredictsRepository
import com.capstoneproject.cvision.data.room.VisionDatabase

object Injection {
    fun provideAuthRepository(context: Context): AuthenticationRepository {
        val authPrefs = AuthPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AuthenticationRepository.getInstance(apiService, authPrefs)
    }

    fun providePredictsRepository(context: Context): PredictsRepository {
        val apiService = ApiConfig.getApiService()
        return PredictsRepository.getInstance(apiService)
    }

    fun provideArticleRepository(context: Context): ArticleRepository {
        val visionDatabase = VisionDatabase.getInstance(context)
        val articleDao = visionDatabase.articleDao()
        return ArticleRepository.getInstance(articleDao)
    }
}