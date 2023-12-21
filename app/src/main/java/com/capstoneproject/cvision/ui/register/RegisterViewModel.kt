package com.capstoneproject.cvision.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.capstoneproject.cvision.data.model.auth.ResponseRegister
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.utils.Result

class RegisterViewModel(private val authenticationRepository: AuthenticationRepository): ViewModel() {
    fun register(name: String, username: String, password: String): LiveData<Result<ResponseRegister>> = authenticationRepository.register(name, username, password)



}