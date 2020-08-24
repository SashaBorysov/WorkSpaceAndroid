package com.workspaceandroid.di.modules

import com.workspaceandroid.ui.login.signIn.SignInFragment
import com.workspaceandroid.ui.main.fragments.FavoritesFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentBuilder {

    @ContributesAndroidInjector
    fun provideSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    fun provideFavoritesFragment(): FavoritesFragment
}
