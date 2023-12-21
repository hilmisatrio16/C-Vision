package com.capstoneproject.cvision.data.model.auth


import com.google.gson.annotations.SerializedName

data class ResponseLogout(
    @SerializedName("error")
    val error: Boolean,
    @SerializedName("message")
    val message: String
)