package com.workspaceandroid

import android.app.Activity
import android.app.Application
import com.workspaceandroid.di.AppComponent
import com.workspaceandroid.di.initInjections
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class BaseApplication : Application(), HasAndroidInjector {

    @Inject lateinit var androidInjector : DispatchingAndroidInjector<Any>

    companion object {
        lateinit var instance: BaseApplication
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = initInjections(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector

    fun getInstance(): BaseApplication {
        return instance
    }
}
