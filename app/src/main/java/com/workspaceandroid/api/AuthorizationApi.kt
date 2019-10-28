package com.workspaceandroid.api

import com.workspaceandroid.data.remote.requests.AuthorizationModel
import com.workspaceandroid.data.remote.responses.AuthorizationResponseBody
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthorizationApi {

    @POST("/api/auth/signin")
    fun authorizationAsync(@Body model: AuthorizationModel): Deferred<Response<AuthorizationResponseBody>>

}