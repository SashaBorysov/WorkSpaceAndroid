package com.workspaceandroid.data.remote.interceptors

import com.workspaceandroid.data.locale.sharedPreference.AppSharedPreferences
import okhttp3.Interceptor
import okhttp3.Response

open class RequestHeaderInterceptor(private val preferences: AppSharedPreferences) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val builder = request.newBuilder()
        if (preferences.getAuthToken().isNotEmpty())
            builder.addHeader("Authorization", "Bearer " + preferences.getAuthToken())
        return chain.proceed(builder.build())
    }
}