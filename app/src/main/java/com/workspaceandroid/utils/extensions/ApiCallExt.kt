package com.workspaceandroid.utils.extensions

import com.workspaceandroid.models.network.NetworkState

suspend fun <R : Any> safeApiCall(call: suspend () -> R, errorString: String, onError: (NetworkState) -> R) = try {
    call.invoke()
} catch (e: Exception) {
    e.printStackTrace()
    onError(NetworkState.error(e))
}