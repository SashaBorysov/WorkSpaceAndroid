package com.workspaceandroid.models.network

enum class AuthorizationStatus {
    LOGGEDIN,
    NOTREGISTERED,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class AuthorizationState private constructor(
    val status: AuthorizationStatus
) {

    companion object {

        val LOGGEDIN = AuthorizationState(AuthorizationStatus.LOGGEDIN)
        val NOTREGISTERED = AuthorizationState(AuthorizationStatus.NOTREGISTERED)
        val FAILED = AuthorizationState(AuthorizationStatus.FAILED)

    }
}