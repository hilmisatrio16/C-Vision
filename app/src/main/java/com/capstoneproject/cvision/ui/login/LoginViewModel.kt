package com.capstoneproject.cvision.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstoneproject.cvision.data.model.auth.ResponseLogin
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.utils.Result
import kotlinx.coroutines.launch

class LoginViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {
    fun login(username: String, password: String): LiveData<Result<ResponseLogin>> = authenticationRepository.login(username, password)

    fun sessionIsActive(token: String, name: String){
        viewModelScope.launch {
            authenticationRepository.saveSessionUser(token, name)
        }
    }

    fun getTokenUser(): LiveData<String>{
        return authenticationRepository.token.asLiveData()
    }
}