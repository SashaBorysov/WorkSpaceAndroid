package com.workspaceandroid.baseui

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.workspaceandroid.utils.extensions.checkPermissionByCode
import com.workspaceandroid.utils.extensions.showSnackBarIfNeverAskSelectedByCode
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    var permissionCallback: (() -> Unit)? = null
    var permissionCancelCallback: (() -> Unit)? = null
    var permissionRequestCode: Int? = null

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionRequestCode) {
            val permissionStatus = checkPermissionByCode(requestCode)
            if (permissionStatus) {
                permissionCallback?.invoke()
                permissionCallback = null
                permissionRequestCode = null
            } else {
                showSnackBarIfNeverAskSelectedByCode(requestCode, permissionCancelCallback)
            }
        }
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> =
            dispatchingAndroidInjector

    protected fun setFragment(fragment: Fragment, containerId: Int) {
        val newFrag = supportFragmentManager.findFragmentByTag(fragment.javaClass.canonicalName)
        if (newFrag != null && newFrag.isVisible) {
            return
        } else {
            replaceFragment(containerId, fragment, fragment.javaClass.canonicalName)
        }
    }

    protected fun addFragmentWithBackStack(fragment: Fragment, containerId: Int) {
        val newFrag = supportFragmentManager.findFragmentByTag(fragment.javaClass.canonicalName)
        if (newFrag != null && newFrag.isVisible) {
            return
        } else {
            replaceFragmentWithBackStack(containerId, fragment, fragment.javaClass.canonicalName)
        }
    }

    private fun replaceFragmentWithBackStack(
            @IdRes id: Int,
            frag: Fragment,
            tag: String? = frag.javaClass.canonicalName
    ) {
        supportFragmentManager.beginTransaction().replace(id, frag, tag).addToBackStack(tag).commitAllowingStateLoss()
    }

    private fun replaceFragment(@IdRes id: Int, frag: Fragment, tag: String? = frag.javaClass.canonicalName) {
        supportFragmentManager.beginTransaction().replace(id, frag, tag).commitAllowingStateLoss()
    }

    protected fun isNetworkAvailable(): Boolean {
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return cm.activeNetworkInfo != null && cm.activeNetworkInfo.isConnected
    }

    interface IOnDialogButtonClickListener {
        fun onButtonClicked()
    }

    interface IOnDialogDoubleButtonClickListener : IOnDialogButtonClickListener {
        fun onLeftButtonClicked()
    }
}
