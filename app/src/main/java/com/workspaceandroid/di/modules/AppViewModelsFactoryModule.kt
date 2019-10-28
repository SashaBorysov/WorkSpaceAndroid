package com.workspaceandroid.di.modules

import com.workspaceandroid.AppViewModelsFactory
import com.workspaceandroid.di.AppViewModelsComponent
import dagger.Module
import dagger.Provides

@Module(subcomponents = [AppViewModelsComponent::class])
class AppViewModelsFactoryModule {

    @Provides
    fun provideAppViewModelFactory(builder: AppViewModelsComponent.Builder): AppViewModelsFactory {
        return AppViewModelsFactory(builder.build())
    }
}