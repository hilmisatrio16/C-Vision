package com.capstoneproject.cvision.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.capstoneproject.cvision.data.dsprefs.AuthPreferences
import com.capstoneproject.cvision.data.model.auth.ResponseLogin
import com.capstoneproject.cvision.data.model.auth.ResponseRegister
import com.capstoneproject.cvision.data.remote.retrofit.ApiService
import com.capstoneproject.cvision.utils.Result
import com.capstoneproject.cvision.utils.handleHttpExceptionMassage
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.net.ConnectException
import kotlin.concurrent.Volatile

class AuthenticationRepository(
    private val apiService: ApiService,
    private val authPrefs: AuthPreferences
) {

    fun login(username: String, password: String): LiveData<Result<ResponseLogin>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userLogin(username, password)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(handleHttpExceptionMassage(e)))
        }catch (e: ConnectException) {
            emit(Result.Error("No internet connection, please connect to the internet"))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred"))
        }
    }

    fun register(name: String,username: String, password: String): LiveData<Result<ResponseRegister>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.userRegister(name, username, password)
            emit(Result.Success(response))
        }catch (e: HttpException){
            emit(Result.Error(handleHttpExceptionMassage(e)))
        }catch (e: ConnectException) {
            emit(Result.Error("No internet connection, please connect to the internet"))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred"))
        }
    }

    fun getProfileUser(token: String): LiveData<Result<Unit>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getProfileUser(token)
            emit(Result.Success(response))
        } catch (e: HttpException) {
            emit(Result.Error(handleHttpExceptionMassage(e)))
        } catch (e: ConnectException) {
            emit(Result.Error("No internet connection, please connect to the internet"))
        } catch (e: Exception) {
            emit(Result.Error("An unexpected error occurred"))
        }
    }

    suspend fun saveSessionUser(token: String, name: String) {
        authPrefs.saveDataPrefs(true, token, name)
    }

    suspend fun clearSession() {
        authPrefs.clearDataPrefs()
    }

    val userIsActive: Flow<Boolean> = authPrefs.getSession()

    val nameUser: Flow<String> = authPrefs.getNameUser()

    val token: Flow<String> = authPrefs.getToken()

    companion object {
        @Volatile
        private var instance: AuthenticationRepository? = null
        fun getInstance(
            apiService: ApiService,
            authPrefs: AuthPreferences
        ): AuthenticationRepository =
            instance ?: synchronized(this) {
                instance ?: AuthenticationRepository(apiService, authPrefs)
            }.also { instance = it }
    }
}