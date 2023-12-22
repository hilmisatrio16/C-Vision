package com.capstoneproject.cvision.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstoneproject.cvision.data.model.auth.InvalidAuthResponse
import com.capstoneproject.cvision.data.model.auth.ResponseLogout
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.utils.Result
import kotlinx.coroutines.launch

class ProfileViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {

    fun userLogout(username: String): LiveData<Result<ResponseLogout>> = authenticationRepository.logout(username)

    fun invalidAuth(username: String): LiveData<Result<InvalidAuthResponse>> = authenticationRepository.invalidAuth(username)

    fun getName(): LiveData<String>{
        return authenticationRepository.nameUser.asLiveData()
    }

    fun getUsername(): LiveData<String>{
        return authenticationRepository.username.asLiveData()
    }

    fun clearSessionUser(){
        viewModelScope.launch {
            authenticationRepository.clearSession()
        }
    }

    fun isSessionActive(): LiveData<Boolean>{
        return authenticationRepository.userIsActive.asLiveData()
    }


}