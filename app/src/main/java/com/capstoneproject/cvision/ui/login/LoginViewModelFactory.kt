package com.capstoneproject.cvision.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.di.Injection
import com.capstoneproject.cvision.ui.register.RegisterViewModelFactory

class LoginViewModelFactory private constructor(private val authenticationRepository: AuthenticationRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(authenticationRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: LoginViewModelFactory? = null
        fun getInstance(context: Context): LoginViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: LoginViewModelFactory(Injection.provideAuthRepository(context))
            }.also { instance = it }
    }
}
