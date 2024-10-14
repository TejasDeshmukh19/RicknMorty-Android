package com.helpers.network

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

sealed interface ApiResponse<T>
data class Success<T>(val data: T) : ApiResponse<T>
data class ApiError<T>(val code: Int? = null, val message: String) : ApiResponse<T>
data class ApiException<T>(val message: String, val exception: Exception) : ApiResponse<T>

