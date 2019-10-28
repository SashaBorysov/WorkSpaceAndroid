package com.workspaceandroid.repositories

import com.workspaceandroid.api.AuthorizationApi
import com.workspaceandroid.data.locale.sharedPreference.AppSharedPreferences
import com.workspaceandroid.data.remote.requests.AuthorizationModel
import com.workspaceandroid.data.remote.responses.AuthorizationResponseBody
import com.workspaceandroid.models.network.NetworkState
import com.workspaceandroid.utils.extensions.logd
import com.workspaceandroid.utils.extensions.safeApiCall
import javax.inject.Inject

class AuthorizationRepository @Inject constructor(
    private val authorizationApi: AuthorizationApi,
    private val sharedPreferences: AppSharedPreferences
) {

    suspend fun authorization(
        email: String,
        password: String,
        onSuccess: suspend (response: AuthorizationResponseBody) -> Unit,
        onError: suspend (Int) -> Unit,
        onNetworkError: (NetworkState) -> Unit
    ) = safeApiCall(
            call = {
                val response = authorizationApi.authorizationAsync(
                        AuthorizationModel(
                                email = email,
                                password = password
                        )
                ).await()

                val data = response.body()

                if (data != null) {
                    data.token?.let {
                        sharedPreferences.setAuthToken(it)
                        logd("authtoken", it)
                    }
                    onSuccess(data)
                } else {
//                    onError(NetworkState.parseError(response.body()?.error))
                    onError(1)
                }
            },
            errorString = NetworkState.NO_INTERNET_CONNECTION
    ) {
        onNetworkError(it)
    }

//    suspend fun signUp(
//            signUpRequest: SignUpRequest,
//            onSuccess: suspend (response: SignUpResponseBody) -> Unit,
//            onError: suspend (Int) -> Unit,
//            onNetworkError: (NetworkState) -> Unit
//    ) = safeApiCall(
//            call = {
//                val response = authorizationApi.makeSignUpRequestAsync(signUpRequest).await()  //TODO set user model to shared preferences
//
//                val data = response.body().getData()
//                if (data != null) {
//                    onSuccess(data)
//                } else {
//                    onError(NetworkState.parseError(response.body()?.error))
//                }
//            },
//            errorString = NetworkState.NO_INTERNET_CONNECTION
//    ) {
//        onNetworkError(it)
//    }

//    suspend fun getProfile(
//            onSuccess: suspend (response: ProfileResponseBody) -> Unit,
//            onError: suspend (Int) -> Unit,
//            onNetworkError: (NetworkState) -> Unit
//    ) = safeApiCall(
//            call = {
//                val response = authorizationApi.makeGetProfileRequestAsync().await()  //TODO set user model to shared preferences
//
//                val data = response.body().getData()
//
//                if (data != null) {
//                    onSuccess(data)
//                } else {
//                    onError(NetworkState.parseError(response.body()?.error))
//                }
//            },
//            errorString = NetworkState.NO_INTERNET_CONNECTION
//    ) {
//        onNetworkError(it)
//    }
}