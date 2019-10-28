package com.workspaceandroid.data.remote.base

import com.google.gson.annotations.SerializedName
import com.workspaceandroid.models.network.CommonServerError

data class ServerErrorResponse(
    @SerializedName("status") val status: String,
    @SerializedName("code") val code: Int,
    @SerializedName("data") val data: List<CommonServerError?>
)