package com.capstoneproject.cvision.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstoneproject.cvision.data.repository.AuthenticationRepository

class SplashScreenViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {

    fun checkSessionActive(): LiveData<Boolean>{
        return authenticationRepository.userIsActive.asLiveData()
    }
}