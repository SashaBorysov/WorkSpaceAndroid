package com.workspaceandroid.ui.main.viewModel

import androidx.lifecycle.MutableLiveData
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseViewModel
import com.workspaceandroid.data.remote.responses.FavoritesResponse
import com.workspaceandroid.models.network.NetworkState
import com.workspaceandroid.repositories.AuthorizationRepository
import com.workspaceandroid.repositories.MainRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(private val mainRepository: MainRepository) :
    BaseViewModel() {

    // for handle requests status
    val requestStatusLiveData by lazy { MutableLiveData<NetworkState>() }

    val favoritesLiveData by lazy { MutableLiveData<List<FavoritesResponse>>() }

    fun getWords() {
        requestStatusLiveData.postValue(NetworkState.LOADING)
        launch {
            mainRepository.getWords(
                {
                    favoritesLiveData.postValue(it)
                    requestStatusLiveData.postValue(NetworkState.SUCCESSFUL)
                    //success

                }, {
                    requestStatusLiveData.postValue(NetworkState.FAILED)
                    //error

                }, {
                    requestStatusLiveData.postValue(NetworkState.error(R.string.error_internet_connection))
                    //network error
                })
        }
    }
}