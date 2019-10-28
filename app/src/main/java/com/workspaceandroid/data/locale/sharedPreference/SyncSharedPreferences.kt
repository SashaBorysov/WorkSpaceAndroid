package com.workspaceandroid.data.locale.sharedPreference

import android.content.Context
import javax.inject.Singleton

@Singleton
class SyncSharedPreferences(context: Context) : AppSharedPreferences {

    companion object {
        const val KEY_AUTH_KEY = " auth_key"
        const val SHARED_PREFERENCES_NAME = "sp_workspace"
    }

    private val sharedPreferences = context.getSharedPreferences(
            SHARED_PREFERENCES_NAME,
            Context.MODE_PRIVATE
    )

    override fun setAuthToken(key: String?) {
        if (key != null) {
            sharedPreferences.edit().putString(KEY_AUTH_KEY, key).apply()
        } else {
            sharedPreferences.edit().putString(KEY_AUTH_KEY, "").apply()
        }
    }

    override fun getAuthToken(): String = sharedPreferences.getString(KEY_AUTH_KEY, "") ?: ""

    /*live data */
//    override fun authStatusLiveData(): SharedPreferenceLiveData<Boolean> {
//        return SharedPreferenceBooleanLiveData(sharedPreferences, KEY, false)
//    }
}