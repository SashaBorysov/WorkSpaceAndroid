package com.workspaceandroid.data.locale.sharedPreference

interface AppSharedPreferences {

    fun setAuthToken(key: String?)
    fun getAuthToken(): String

    /*live data*/
//    fun authStatusLiveData(): SharedPreferenceLiveData<Boolean>
}