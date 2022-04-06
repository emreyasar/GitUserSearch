package com.yasaremre.gitusersearch.network.interceptor

import com.yasaremre.gitusersearch.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

/**
 * This interceptor adds "Authorization:Token XXXXXXX" to the header
 * of the requests sent to the url specified by API_BASE_URL.
 * */

class ApiKeyInterceptor : Interceptor {
    companion object {
        const val AUTHORIZATION_HEADER_KEY = "Authorization"
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return if (request.url.toString().startsWith(BuildConfig.API_BASE_URL)) {
            val newRequest = request.newBuilder()
                .addHeader(AUTHORIZATION_HEADER_KEY, "Token ${BuildConfig.API_KEY}")
                .build()
            chain.proceed(newRequest)
        } else {
            chain.proceed(request)
        }
    }
}