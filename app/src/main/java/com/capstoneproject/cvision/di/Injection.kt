package com.capstoneproject.cvision.di

import android.content.Context
import com.capstoneproject.cvision.data.dsprefs.AuthPreferences
import com.capstoneproject.cvision.data.dsprefs.dataStore
import com.capstoneproject.cvision.data.remote.retrofit.ApiConfig
import com.capstoneproject.cvision.data.repository.AuthenticationRepository

object Injection {
    fun provideAuthRepository(context: Context): AuthenticationRepository{
        val authPrefs = AuthPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return AuthenticationRepository.getInstance(apiService, authPrefs)
    }
}