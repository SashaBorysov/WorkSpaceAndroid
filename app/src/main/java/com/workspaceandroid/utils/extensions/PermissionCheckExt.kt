package com.workspaceandroid.utils.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.snackbar.Snackbar
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseActivity

const val REQUEST_PERMISSIONS_CODE_LOCATION = 100
const val REQUEST_PERMISSIONS_CODE_CAMERA = 101
const val REQUEST_PERMISSIONS_EXTERNAL_STORAGE = 102
const val REQUEST_PERMISSIONS_CODE_SMS = 103
const val REQUEST_PERMISSIONS_MULTIMEDIA_ = 104
const val REQUEST_PERMISSIONS_CODE_CALL_PHONE = 105

val smsPermissionsList: Array<String>
    get() = arrayOf(Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS)

val locationPermissionsList: Array<String>
    get() = arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)

val storagePermissionsList: Array<String>
    get() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)

val cameraPermissionsList: Array<String>
    get() = arrayOf(Manifest.permission.CAMERA)

val callPhonePermissionsList: Array<String>
    get() = arrayOf(Manifest.permission.CALL_PHONE)

val multimediaPermissionsList: Array<String>
    get() = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE/*, Manifest.permission.CAMERA*/)

fun BaseActivity.requestSmsPermissions(function: () -> Unit) {
    requestPermissions(
            smsPermissionsList,
            REQUEST_PERMISSIONS_CODE_SMS, function
    )
}

fun BaseActivity.requestLocationPermissions(function: () -> Unit) {
    requestPermissions(
            locationPermissionsList,
            REQUEST_PERMISSIONS_CODE_LOCATION,
            function
    )
}

/*with function that called when user deny permission*/
fun BaseActivity.requestLocationPermissions(function: () -> Unit, cancelFunction: () -> Unit) {
    requestPermissions(
            locationPermissionsList,
            REQUEST_PERMISSIONS_CODE_LOCATION,
            function,
            cancelFunction
    )
}

fun BaseActivity.requestCameraPermissions(function: () -> Unit) {
    requestPermissions(
            cameraPermissionsList,
            REQUEST_PERMISSIONS_CODE_CAMERA, function
    )
}

fun BaseActivity.requestCallPhonePermissions(function: () -> Unit) {
    requestPermissions(
            callPhonePermissionsList,
            REQUEST_PERMISSIONS_CODE_CALL_PHONE,
            function
    )
}

fun BaseActivity.requestExternalStoragePermissions(function: () -> Unit) {
    requestPermissions(
            storagePermissionsList,
            REQUEST_PERMISSIONS_EXTERNAL_STORAGE, function
    )
}

fun BaseActivity.requestMultimediaPermissions(function: () -> Unit) {
    requestPermissions(
            multimediaPermissionsList,
            REQUEST_PERMISSIONS_MULTIMEDIA_, function
    )
}

private fun BaseActivity.requestPermissions(
        permissionsList: Array<String>,
        requestCode: Int,
        function: () -> Unit,
        cancelFunction: (() -> Unit)? = null
) {
    this.permissionCallback = function
    this.permissionCancelCallback = cancelFunction
    this.permissionRequestCode = requestCode
    if (checkPermissions(permissionsList)) {
        permissionCallback?.invoke()
        permissionCallback = null
        permissionRequestCode = null
        permissionCancelCallback = null
    } else {
        ActivityCompat.requestPermissions(
                this,
                permissionsList,
                requestCode
        )
    }
}

fun Context.checkPermissionByCode(code: Int): Boolean {
    return when (code) {
        REQUEST_PERMISSIONS_CODE_LOCATION -> checkLocationPermissions()
        REQUEST_PERMISSIONS_CODE_CAMERA -> checkCameraPermissions()
        REQUEST_PERMISSIONS_EXTERNAL_STORAGE -> checkStoragePermissions()
        REQUEST_PERMISSIONS_CODE_SMS -> checkSmsPermissions()
        REQUEST_PERMISSIONS_MULTIMEDIA_ -> checkMultimediaPermissions()
        REQUEST_PERMISSIONS_CODE_CALL_PHONE -> checkCallPhonePermissions()
        else -> false
    }
}

fun Context.checkSmsPermissions(): Boolean = checkPermissions(smsPermissionsList)

fun Context.checkLocationPermissions(): Boolean = checkPermissions(locationPermissionsList)

fun Context.checkCameraPermissions(): Boolean = checkPermissions(cameraPermissionsList)

fun Context.checkStoragePermissions(): Boolean = checkPermissions(storagePermissionsList)

fun Context.checkCallPhonePermissions(): Boolean = checkPermissions(callPhonePermissionsList)

fun Context.checkMultimediaPermissions(): Boolean = checkPermissions(multimediaPermissionsList)

fun Context.checkPermissions(permissions: Array<String>): Boolean {
    return permissions.all {
        ContextCompat.checkSelfPermission(
                this,
                it
        ) == PackageManager.PERMISSION_GRANTED
    }
}

fun Activity.showSnackBarIfNeverAskSelectedByCode(code: Int, function: (() -> Unit)? = null) {
    val checkedPermissions: Array<String>? = when (code) {
        REQUEST_PERMISSIONS_CODE_LOCATION -> locationPermissionsList
        REQUEST_PERMISSIONS_CODE_CAMERA -> cameraPermissionsList
        REQUEST_PERMISSIONS_EXTERNAL_STORAGE -> storagePermissionsList
        REQUEST_PERMISSIONS_CODE_SMS -> smsPermissionsList
        REQUEST_PERMISSIONS_CODE_CALL_PHONE -> callPhonePermissionsList
        else -> null
    }

    if (checkedPermissions != null && isNeverAskSelected(this, checkedPermissions)) {
        showSnackBarIfNeverAskSelected()
    } else {
        function?.let { it() }
    }
}

fun Activity.showSnackBarIfNeverAskSelected() {
    val contentView = this.findViewById<ViewGroup>(android.R.id.content).getChildAt(0)
    showSnackBabAboutRequestingPermission(contentView, this as FragmentActivity)
}

private fun isNeverAskSelected(activity: Activity, checkedPermissions: Array<String>): Boolean =
        checkedPermissions.any { !ActivityCompat.shouldShowRequestPermissionRationale(activity, it) }

private fun showSnackBabAboutRequestingPermission(view: View, activity: FragmentActivity) {
    val context = view.context
    dismissOpenedDialogs(activity)
    Snackbar.make(view, R.string.message_permissions_for_this_feature_are_switched_off, Snackbar.LENGTH_LONG)
            .setAction(R.string.action_enable) {
                val intent = Intent()
                intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                val uri = Uri.fromParts("package", context.packageName, null)
                intent.data = uri
                context.startActivity(intent)
            }
            .setActionTextColor(ContextCompat.getColor(context, R.color.colorAccent))
            .show()
}

private fun dismissOpenedDialogs(activity: FragmentActivity) {
    val fragments = activity.supportFragmentManager.fragments
    for (fragment in fragments) {
        val childFragments = fragment.childFragmentManager.fragments
        for (childFragment in childFragments) {
            if (childFragment is DialogFragment) {
                childFragment.dismiss()
            }
        }
        if (fragment is DialogFragment) {
            fragment.dismiss()
        }
    }
}
