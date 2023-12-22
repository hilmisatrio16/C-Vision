package com.capstoneproject.cvision.ui.splashscreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.di.Injection

class SplashScreenViewModelFactory (private val authenticationRepository: AuthenticationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SplashScreenViewModel::class.java)) {
            return SplashScreenViewModel(authenticationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: SplashScreenViewModelFactory? = null
        fun getInstance(context: Context): SplashScreenViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: SplashScreenViewModelFactory(Injection.provideAuthRepository(context))
            }.also { instance = it }
    }
}