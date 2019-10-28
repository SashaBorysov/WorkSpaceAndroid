package com.workspaceandroid.models.network

import com.google.gson.annotations.SerializedName

data class CommonServerErrors(
    val errorCode: String,
    @SerializedName("error") val error: List<String>
)