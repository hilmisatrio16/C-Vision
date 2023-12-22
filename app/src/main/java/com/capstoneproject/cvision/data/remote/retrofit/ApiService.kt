package com.capstoneproject.cvision.data.remote.retrofit

import com.capstoneproject.cvision.data.model.auth.InvalidAuthResponse
import com.capstoneproject.cvision.data.model.auth.ResponseLogin
import com.capstoneproject.cvision.data.model.auth.ResponseLogout
import com.capstoneproject.cvision.data.model.auth.ResponseRegister
import com.capstoneproject.cvision.data.model.predict.ResponsePrediction
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ): ResponseLogin

    @FormUrlEncoded
    @POST("register")
    suspend fun userRegister(
        @Field("name") name: String,
        @Field("username") username: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("logout")
    suspend fun userLogout(
        @Field("username") username: String
    ): ResponseLogout

    @POST("invalidate_auth")
    suspend fun invalidateAuth(
        @Body data: UserData
    ): InvalidAuthResponse

    @Multipart
    @POST("predict")
    suspend fun predictCataract(
        @Part("token") token: RequestBody,
        @Part file: MultipartBody.Part
    ): ResponsePrediction
}

data class UserData(
    val username: String
)