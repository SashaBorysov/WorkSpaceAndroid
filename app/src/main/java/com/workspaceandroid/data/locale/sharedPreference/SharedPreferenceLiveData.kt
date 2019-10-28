package com.workspaceandroid.data.locale.sharedPreference

import android.content.SharedPreferences
import androidx.lifecycle.LiveData

abstract class SharedPreferenceLiveData<T>(var sharedPrefs: SharedPreferences, var key: String, var defValue: T) : LiveData<T>() {

    private val preferenceChangeListener = SharedPreferences.OnSharedPreferenceChangeListener { _, key ->
        if (this@SharedPreferenceLiveData.key == key) {
            value = getValueFromPreferences(key, defValue)
        }
    }

    internal abstract fun getValueFromPreferences(key: String, defValue: T): T

    override fun onActive() {
        super.onActive()
        value = getValueFromPreferences(key, defValue)
        sharedPrefs.registerOnSharedPreferenceChangeListener(preferenceChangeListener)
    }

    override fun onInactive() {
        sharedPrefs.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener)
        super.onInactive()
    }

}

class SharedPreferenceBooleanLiveData(prefs: SharedPreferences, key: String, defValue: Boolean) : SharedPreferenceLiveData<Boolean>(prefs, key, defValue) {
    override fun getValueFromPreferences(key: String, defValue: Boolean): Boolean {
        return sharedPrefs.getBoolean(key, defValue)
    }
}
