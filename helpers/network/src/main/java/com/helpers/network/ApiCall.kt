package com.helpers.network

import retrofit2.Response

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

inline fun <reified T> apiCall(call: () -> Response<T>): ApiResponse<T> {
    return try {
        val response = call()
        val body = response.body()
        val code = response.code()
        val errorBody = response.errorBody()
        if (response.isSuccessful && body != null) {
            Success(data = body)
        } else if (errorBody != null) {
            ApiError(code = code, message = errorBody.string())
        } else {
            ApiError(code = code, message = "Failed, Please try again later.")
        }
    } catch (e: Exception) {
        return ApiException(
            message = e.message.toString(),
            exception = e
        )
    }
}