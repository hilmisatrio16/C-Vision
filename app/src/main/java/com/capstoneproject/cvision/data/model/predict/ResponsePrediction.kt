package com.capstoneproject.cvision.data.model.predict


import com.google.gson.annotations.SerializedName

data class ResponsePrediction(
    @SerializedName("Error")
    val error: Boolean,
    @SerializedName("message")
    val message: String,
    @SerializedName("Penanganan")
    val penanganan: Penanganan
)