package com.capstoneproject.cvision.utils

import android.content.Context
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import retrofit2.HttpException
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private const val FILENAME_FORMAT = "yyyyMMdd_HHmmss"
private val timeStamp: String = SimpleDateFormat(FILENAME_FORMAT, Locale.US).format(Date())

fun createCustomTempFile(context: Context): File {
    val filesDir = context.externalCacheDir
    return File.createTempFile(timeStamp, ".jpg", filesDir)
}

fun handleHttpExceptionMassage(e: HttpException): String {
    val jsonInString = e.response()?.errorBody()?.string()
    val errorBodyResponse = Gson().fromJson(jsonInString, ErrorResponse::class.java)
    return errorBodyResponse.message.toString()
}


//sementara modelnya
data class ErrorResponse(
    @field:SerializedName("error")
    val error: Boolean? = null,
    @field:SerializedName("message")
    val message: String? = null
)