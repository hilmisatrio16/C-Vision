package com.capstoneproject.cvision.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstoneproject.cvision.data.model.predict.RequestPredict
import com.capstoneproject.cvision.data.model.predict.ResponsePrediction
import com.capstoneproject.cvision.data.remote.retrofit.ApiService
import com.capstoneproject.cvision.utils.Result
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class PredictsRepository(
    private val apiService: ApiService
) {

    fun predictCataract(
        token: String,
        dataPredict: RequestPredict
    ): LiveData<Result<ResponsePrediction>> = liveData {
        emit(Result.Loading)
        val requestToken = dataPredict.token.toRequestBody("text/plain".toMediaType())
        val requestEyeImageFile = dataPredict.imageFile.asRequestBody("image/jpeg".toMediaType())
        val multipartBody = MultipartBody.Part.createFormData(
            "file",
            dataPredict.imageFile.name,
            requestEyeImageFile
        )

        try {
            val response = apiService.predictCataract(requestToken, multipartBody)
            emit(Result.Success(response))
        }catch (e: Exception){
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: PredictsRepository? = null
        fun getInstance(
            apiService: ApiService
        ): PredictsRepository =
            instance ?: synchronized(this) {
                instance ?: PredictsRepository(apiService)
            }.also { instance = it }
    }
}