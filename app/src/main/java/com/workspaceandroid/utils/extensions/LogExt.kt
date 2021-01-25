package com.workspaceandroid.utils.extensions

import android.util.Log
import com.workspaceandroid.BuildConfig

fun logd(tag: String, text: String?) {
    if (!BuildConfig.ENABLE_LOGS) return
    Log.d(tag, text!!) //TODO !!
}

fun loge(tag: String, text: String?, tr: Exception?) {
    if (!BuildConfig.ENABLE_LOGS) return
    Log.e(tag, text, tr)
}

fun loge(tag: String, text: String?) {
    if (!BuildConfig.ENABLE_LOGS) return
    Log.e(tag, text!!)
}