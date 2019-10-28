package com.workspaceandroid.ui.login.viewModel

import androidx.lifecycle.MutableLiveData
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseViewModel
import com.workspaceandroid.models.network.AuthorizationState
import com.workspaceandroid.models.network.NetworkState
import com.workspaceandroid.repositories.AuthorizationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val authorizationRepository: AuthorizationRepository) :
    BaseViewModel() {

    // for handle requests status
    val requestStatusLiveData by lazy { MutableLiveData<NetworkState>() }

    //sendAuthorization status, true when request is successful
    val sendAuthorizationLiveData by lazy { MutableLiveData<Boolean>() }

    //authorization status, true when request is successful
    val authorizationLiveData by lazy { MutableLiveData<Boolean>() }

    //signup status, true when request is successful
    val signUpLiveData by lazy { MutableLiveData<Boolean>() }
    
    fun authorization(
        email: String,
        password: String
    ) {
        requestStatusLiveData.postValue(NetworkState.LOADING)
        launch {
            authorizationRepository.authorization(
                email,
                password, {
                    if (it.token != null) {
                        // authorizationStatusLiveData.postValue(AuthorizationState.NOTREGISTERED)
                        authorizationLiveData.postValue(true)
                    } else {
                        authorizationLiveData.postValue(false)
                    }
                    requestStatusLiveData.postValue(NetworkState.SUCCESSFUL)
                    //success

                }, {
                    requestStatusLiveData.postValue(NetworkState.FAILED)
                    authorizationLiveData.postValue(false)
                    //error

                }, {
                    requestStatusLiveData.postValue(NetworkState.error(R.string.error_internet_connection))
                    authorizationLiveData.postValue(false)
                    //network error
                })
        }
    }

//    fun registration(
//        signUpRequest: SignUpRequest
//    ) {
//        requestStatusLiveData.postValue(NetworkState.LOADING)
//        launch {
//            authorizationRepository.signUp(
//                signUpRequest,
//                {
//                    authorizationStatusLiveData.postValue(AuthorizationState.LOGGEDIN)
//                    signUpLiveData.postValue(true)
//                    requestStatusLiveData.postValue(NetworkState.SUCCESSFUL)
//                    //success
//
//                }, {
//                    requestStatusLiveData.postValue(NetworkState.error(it))
//                    signUpLiveData.postValue(false)
//                    //error
//
//                }, {
//                    requestStatusLiveData.postValue(NetworkState.error(R.string.error_internet_connection))
//                    authorizationLiveData.postValue(false)
//                    //network error
//                })
//        }
//    }

//    fun getProfile() {
//        authorizationStatusLiveData.postValue(AuthorizationState.FAILED)
//        requestStatusLiveData.postValue(NetworkState.LOADING)
//        launch {
//            authorizationRepository.getProfile(
//                {
//                    it.firstName?.let { authorizationStatusLiveData.postValue(AuthorizationState.LOGGEDIN)
//                    } ?: run {
//                        authorizationStatusLiveData.postValue(AuthorizationState.NOTREGISTERED)
//                    }
//                    requestStatusLiveData.postValue(NetworkState.SUCCESSFUL)
//                    //success
//
//                }, {
//                    requestStatusLiveData.postValue(NetworkState.FAILED)
//                    //error
//
//                }, {
//                    requestStatusLiveData.postValue(NetworkState.NO_INTERNET)
//                    //network error
//                })
//        }
//    }
}