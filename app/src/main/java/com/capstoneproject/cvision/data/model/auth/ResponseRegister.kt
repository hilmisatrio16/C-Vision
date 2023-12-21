package com.capstoneproject.cvision.data.model.auth


import com.google.gson.annotations.SerializedName

data class ResponseRegister(
    @SerializedName("Error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("token")
    val token: String
)