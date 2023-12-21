package com.capstoneproject.cvision.data.model.predict

import java.io.File

data class RequestPredict(
    val token: String,
    val imageFile: File
)
