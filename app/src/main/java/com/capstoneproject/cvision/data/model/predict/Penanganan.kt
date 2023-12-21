package com.capstoneproject.cvision.data.model.predict


import com.google.gson.annotations.SerializedName

data class Penanganan(
    @SerializedName("Class terprediksi")
    val classTerprediksi: String,
    @SerializedName("Terdeteksi Jenis")
    val terdeteksiJenis: String
)