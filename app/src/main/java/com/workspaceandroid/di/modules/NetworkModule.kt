package com.workspaceandroid.di.modules

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.workspaceandroid.BuildConfig
import com.workspaceandroid.data.locale.sharedPreference.AppSharedPreferences
import com.workspaceandroid.data.remote.interceptors.CloseConnectionInterceptor
import com.workspaceandroid.data.remote.interceptors.RequestHeaderInterceptor
import com.workspaceandroid.data.remote.interceptors.TokenInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @Singleton
    @JvmStatic
    fun provideRetrofit(
        httpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .client(httpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        tokenInterceptor: TokenInterceptor,
        requestHeaderInterceptor: RequestHeaderInterceptor,
        closeConnectionInterceptor: CloseConnectionInterceptor
    ): OkHttpClient {
        val builder = OkHttpClient.Builder()
            .addInterceptor(tokenInterceptor)
            .addInterceptor(requestHeaderInterceptor)
            .addNetworkInterceptor(closeConnectionInterceptor)
            .retryOnConnectionFailure(true)
            .readTimeout(18, TimeUnit.SECONDS)
            .connectTimeout(18, TimeUnit.SECONDS)

        if (BuildConfig.ENABLE_LOGS) builder.addInterceptor(httpLoggingInterceptor)

        return builder.build()
    }


    @Singleton
    @Provides
    @Named("google")
    @JvmStatic
    fun provideGoogleRetrofit(httpClient: OkHttpClient, gSon: Gson): Retrofit {
        return Retrofit.Builder()
                .client(httpClient)
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create(gSon))
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .build()
    }

    @Provides
    @Singleton
    @JvmStatic
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideTokenInterceptor(preferences: AppSharedPreferences): TokenInterceptor {
        return TokenInterceptor(preferences)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideRequestHeaderInterceptor(preferences: AppSharedPreferences): RequestHeaderInterceptor {
        return RequestHeaderInterceptor(preferences)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideCloseConnectionInterceptor(): CloseConnectionInterceptor {
        return CloseConnectionInterceptor()
    }

    @Singleton
    @Provides
    @JvmStatic
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .create()
    }
}