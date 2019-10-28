package com.workspaceandroid.data.remote.requests

import com.google.gson.annotations.SerializedName

data class AuthorizationModel(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)