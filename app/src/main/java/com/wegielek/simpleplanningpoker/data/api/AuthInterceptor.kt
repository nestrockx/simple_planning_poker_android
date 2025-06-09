package com.wegielek.simpleplanningpoker.data.api

import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    private val tokenProvider: () -> String?,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        val token = tokenProvider()

        if (!token.isNullOrBlank()) {
            requestBuilder.addHeader("Authorization", "Token $token")
        }

        return chain.proceed(requestBuilder.build())
    }
}
