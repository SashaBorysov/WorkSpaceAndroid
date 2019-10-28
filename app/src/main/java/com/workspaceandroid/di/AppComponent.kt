package com.workspaceandroid.di

import com.workspaceandroid.BaseApplication
import com.workspaceandroid.di.modules.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/*Global Component*/
@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ActivityBuilder::class,
        AppApiModule::class,
        AppViewModelsFactoryModule::class,
        FragmentBuilder::class,
        NetworkModule::class,
        SharedPreferenceModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: BaseApplication): Builder

        fun build(): AppComponent
    }

    fun inject(app: BaseApplication)
}