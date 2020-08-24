package com.workspaceandroid.api

import com.workspaceandroid.data.remote.requests.AuthorizationModel
import com.workspaceandroid.data.remote.responses.AuthorizationResponseBody
import com.workspaceandroid.data.remote.responses.FavoritesResponse
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface FavoritesApi {

    @GET("/api/products")
    fun favoritesAsync(): Deferred<Response<List<FavoritesResponse>>>

}