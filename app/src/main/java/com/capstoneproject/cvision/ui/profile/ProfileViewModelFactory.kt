package com.capstoneproject.cvision.ui.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.di.Injection
import com.capstoneproject.cvision.ui.splashscreen.SplashScreenViewModel
import com.capstoneproject.cvision.ui.splashscreen.SplashScreenViewModelFactory

class ProfileViewModelFactory (private val authenticationRepository: AuthenticationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(authenticationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: ProfileViewModelFactory? = null
        fun getInstance(context: Context): ProfileViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ProfileViewModelFactory(Injection.provideAuthRepository(context))
            }.also { instance = it }
    }
}