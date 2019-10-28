package com.workspaceandroid.di

import com.workspaceandroid.ui.login.viewModel.LoginViewModel
import dagger.Subcomponent

@Subcomponent
interface AppViewModelsComponent {

    @Subcomponent.Builder
    interface Builder {
        fun build(): AppViewModelsComponent
    }

    fun provideLoginViewModel(): LoginViewModel

}