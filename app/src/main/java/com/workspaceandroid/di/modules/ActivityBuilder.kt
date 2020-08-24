package com.workspaceandroid.di.modules

import com.workspaceandroid.ui.login.LoginActivity
import com.workspaceandroid.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {

    @ContributesAndroidInjector
    fun contributeLoginActivity(): LoginActivity

    @ContributesAndroidInjector
    fun contributeMainActivity(): MainActivity
}
