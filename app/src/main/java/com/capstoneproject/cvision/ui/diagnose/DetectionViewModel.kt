package com.capstoneproject.cvision.ui.diagnose

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstoneproject.cvision.data.model.predict.RequestPredict
import com.capstoneproject.cvision.data.repository.AuthenticationRepository
import com.capstoneproject.cvision.data.repository.PredictsRepository

class DetectionViewModel(
    private val predictsRepository: PredictsRepository,
    private val authenticationRepository: AuthenticationRepository
) : ViewModel() {

    fun predictCataract(token: String, dataPredict: RequestPredict) =
        predictsRepository.predictCataract(token, dataPredict)

    fun getTokenUser(): LiveData<String> {
        return authenticationRepository.token.asLiveData()
    }
}