package com.workspaceandroid

import android.app.Activity
import android.app.Application
import com.workspaceandroid.di.AppComponent
import com.workspaceandroid.di.initInjections
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class BaseApplication : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingInjector: DispatchingAndroidInjector<Activity>

    companion object {
        lateinit var instance: BaseApplication
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        appComponent = initInjections(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> =
        dispatchingInjector

    fun getInstance(): BaseApplication {
        return instance
    }
}
