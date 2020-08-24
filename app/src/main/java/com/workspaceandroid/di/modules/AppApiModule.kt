package com.workspaceandroid.di.modules

import com.workspaceandroid.api.AuthorizationApi
import com.workspaceandroid.api.FavoritesApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
object AppApiModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideAuthorizationApi(retrofit: Retrofit): AuthorizationApi =
            retrofit.create(AuthorizationApi::class.java)

    @Singleton
    @JvmStatic
    @Provides
    fun provideFavoritesApi(retrofit: Retrofit): FavoritesApi =
        retrofit.create(FavoritesApi::class.java)

//    @Singleton
//    @JvmStatic
//    @Provides
//    fun provideGoogleApi(@Named("google") retrofit: Retrofit): GoogleApi =
//            retrofit.create(GoogleApi::class.java)

}
