package com.workspaceandroid.data.remote.interceptors

import okhttp3.Interceptor
import okhttp3.Response

open class CloseConnectionInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        builder.addHeader("Connection", "close")
        return chain.proceed(builder.build())
    }
}