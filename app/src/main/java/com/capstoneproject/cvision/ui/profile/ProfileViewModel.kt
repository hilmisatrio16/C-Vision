package com.capstoneproject.cvision.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstoneproject.cvision.data.repository.AuthenticationRepository

class ProfileViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {
    fun getName(): LiveData<String>{
        return authenticationRepository.nameUser.asLiveData()
    }
}