package com.helpers.network

import com.helpers.network.api_utils.ApiUtils
import com.helpers.network.api_utils.ApiUtils.isNoInternetException
import com.helpers.network.api_utils.ApiUtils.parseApiError

/**
 * Created by Tejas Deshmukh on 27/09/24.
 */

sealed interface ApiResponse<T>
data class Loading<T>(val isLoading: Boolean): ApiResponse<T>
data class Success<T>(val data: T) : ApiResponse<T>
data class ApiError<T>(val code: Int? = null, val message: String) : ApiResponse<T>
data class ApiException<T>(val message: String, val exception: Exception) : ApiResponse<T>

sealed interface Error
data class ServerError(val message: String): Error
data object NetworkError: Error


fun <T> ApiResponse<T>.onSuccess(responseData: (T) -> Unit): ApiResponse<T> {
    if (this is Success) {
        responseData(this.data)
    }
    return this
}

fun <T> ApiResponse<T>.onFailure(apiError: (Error) -> Unit): ApiResponse<T> {
    if (this is ApiError) {
        apiError(ServerError(message = this.parseApiError()))
    }

    if (this is ApiException) {
        if(this.isNoInternetException()) {
            apiError(NetworkError)
        } else {
            apiError(ServerError(message = ApiUtils.getMessageFor(this.exception)))
        }
    }
    return this
}

fun <T> ApiResponse<T>.onLoading(isLoading: (Boolean) -> Unit): ApiResponse<T> {
    if (this is Loading) {
        isLoading(this.isLoading)
    }
    return this
}

