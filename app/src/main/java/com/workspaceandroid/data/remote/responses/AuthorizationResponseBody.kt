package com.workspaceandroid.data.remote.responses

import com.google.gson.annotations.SerializedName

data class AuthorizationResponseBody(
    @SerializedName("accessToken") val token: String?
)