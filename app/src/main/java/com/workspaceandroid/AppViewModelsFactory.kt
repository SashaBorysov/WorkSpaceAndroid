package com.workspaceandroid

import androidx.collection.ArrayMap
import androidx.lifecycle.ViewModel
import com.workspaceandroid.di.AppViewModelsComponent
import com.workspaceandroid.ui.login.viewModel.LoginViewModel
import com.workspaceandroid.ui.main.viewModel.MainViewModel
import java.util.concurrent.Callable

class AppViewModelsFactory(private val appViewModelsComponent: AppViewModelsComponent) :
        BaseViewModelFactory() {

    override fun fillViewModels(creators: ArrayMap<Class<*>, Callable<out ViewModel>>) {

        creators[LoginViewModel::class.java] = Callable { appViewModelsComponent.provideLoginViewModel() }
        creators[MainViewModel::class.java] = Callable { appViewModelsComponent.provideMainViewModel() }
    }
}
