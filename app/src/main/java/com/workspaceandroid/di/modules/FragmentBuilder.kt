package com.workspaceandroid.di.modules

import com.workspaceandroid.ui.login.signIn.SignInFragment
import com.workspaceandroid.ui.main.fragments.SearchFragment
import com.workspaceandroid.ui.main.fragments.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector
    fun provideSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    fun provideFavoritesFragment(): SearchFragment

    @ContributesAndroidInjector
    fun provideSettingsFragment(): SettingsFragment
}
