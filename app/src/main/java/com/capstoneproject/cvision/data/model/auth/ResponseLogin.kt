package com.capstoneproject.cvision.data.model.auth


import com.google.gson.annotations.SerializedName

data class ResponseLogin(
    @SerializedName("Error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String,
    @SerializedName("Your Name")
    val yourName: String,
    @SerializedName("Your Username")
    val yourUsername: String
)