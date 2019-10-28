package com.workspaceandroid.di.modules

import com.workspaceandroid.BaseApplication
import com.workspaceandroid.data.locale.sharedPreference.AppSharedPreferences
import com.workspaceandroid.data.locale.sharedPreference.SyncSharedPreferences
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class SharedPreferenceModule {

    @Singleton
    @Provides
    fun provideSyncSharedPreferences(app: BaseApplication): AppSharedPreferences =
        SyncSharedPreferences(app)
}