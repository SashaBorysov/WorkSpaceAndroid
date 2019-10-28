package com.workspaceandroid.di.modules

import com.workspaceandroid.ui.login.LoginActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface ActivityBuilder {

    @ContributesAndroidInjector
    fun contributeLoginActivity(): LoginActivity

}
