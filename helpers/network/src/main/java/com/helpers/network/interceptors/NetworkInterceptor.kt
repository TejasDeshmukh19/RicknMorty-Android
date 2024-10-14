package com.helpers.network.interceptors

import android.content.Context
import android.net.ConnectivityManager
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


class NetworkInterceptor @Inject constructor(@ApplicationContext val context: Context) :
    Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        if (isConnectedToInternet().not()) {
            throw NoNetworkException()
        }
        return chain.proceed(chain.request())
    }

    private fun isConnectedToInternet(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val netInfo = cm.activeNetworkInfo
        return (netInfo != null && netInfo.isConnected)
    }

    class NoNetworkException internal constructor() : IOException("Please check Network Connection")
}