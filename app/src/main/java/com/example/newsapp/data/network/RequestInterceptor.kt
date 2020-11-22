package com.example.newsapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

const val API_KEY = "81309c3ebd6a4b7ea3b9a9fc61a91006"

class RequestInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request()
            .url()
            .newBuilder()
            .addQueryParameter("apiKey",
                API_KEY
            )
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return chain.proceed(request)
    }
}