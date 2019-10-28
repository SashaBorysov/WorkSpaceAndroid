package com.workspaceandroid.ui.login

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseActivity
import com.workspaceandroid.ui.login.signIn.SignInFragment

class LoginActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        showSignInFragment()
        setListeners()
    }

    private fun setListeners() {

    }

    private fun showSignInFragment() {
        replaceLoginContainerFragments(R.id.container, SignInFragment())
    }

//    private fun showSplashFragment() {
//        setFragment(SplashFragment(), R.id.container)
//    }

    private fun replaceLoginContainerFragments(
        @IdRes id: Int,
        frag: Fragment,
        tag: String? = frag.javaClass.canonicalName
    ) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.slide_in_right,
                R.anim.slide_out_left,
                R.anim.slide_in_left,
                R.anim.slide_out_right)
            .replace(id, frag, tag)
            .addToBackStack(tag)
            .commitAllowingStateLoss()
    }
}