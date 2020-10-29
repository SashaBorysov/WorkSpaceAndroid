package com.workspaceandroid.repositories

import com.workspaceandroid.api.FavoritesApi
import com.workspaceandroid.data.locale.sharedPreference.AppSharedPreferences
import com.workspaceandroid.data.remote.responses.FavoritesResponse
import com.workspaceandroid.models.network.NetworkState
import com.workspaceandroid.utils.extensions.safeApiCall
import javax.inject.Inject

class MainRepository @Inject constructor(
    private val favoritesApi: FavoritesApi,
    private val sharedPreferences: AppSharedPreferences
) {

    suspend fun getProducts(
        onSuccess: suspend (response: List<FavoritesResponse>) -> Unit,
        onError: suspend (Int) -> Unit,
        onNetworkError: (NetworkState) -> Unit
    ) = safeApiCall(
        call = {
            val response = favoritesApi.productsAsync().await()

            val data = response.body()

            if (data != null) {
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
}