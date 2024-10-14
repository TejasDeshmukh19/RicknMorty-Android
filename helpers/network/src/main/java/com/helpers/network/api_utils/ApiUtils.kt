package com.helpers.network.api_utils

import com.helpers.network.ApiConstants.KEY_ERROR
import com.helpers.network.ApiError
import com.helpers.network.ApiException
import com.helpers.network.interceptors.NetworkInterceptor
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

/**
 * Created by Tejas Deshmukh on 14/10/24.
 */


object ApiUtils {

    fun getMessageFor(exception: Exception): String {
        return when (exception) {
            is NetworkInterceptor.NoNetworkException -> {
                "No Internet connectivity."
            }

            is SocketTimeoutException -> {
                "Request Time out."
            }

            is UnknownHostException -> {
                "Request Time out."
            }

            is IOException -> {
                "Failed, Please try again later."
            }

            else -> {
                "Failed, Please try again later."
            }
        }
    }

    fun <T> ApiException<T>.isNoInternetException(): Boolean {
        return exception is NetworkInterceptor.NoNetworkException || exception is UnknownHostException
    }

    fun <T> ApiError<T>.parseApiError(): String? {
        return try {
            val errorJson = JSONObject(message)
            errorJson.optString(KEY_ERROR)
        } catch (e: Exception) {
            null
        }
    }
}