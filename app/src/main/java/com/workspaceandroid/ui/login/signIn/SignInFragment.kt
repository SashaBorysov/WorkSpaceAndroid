package com.workspaceandroid.ui.login.signIn

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.workspaceandroid.AppViewModelsFactory
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseFragment
import com.workspaceandroid.models.network.ApiRequestStatus
import com.workspaceandroid.models.network.AuthorizationStatus
import com.workspaceandroid.ui.login.viewModel.LoginViewModel
import com.workspaceandroid.utils.extensions.logd
import kotlinx.android.synthetic.main.fragment_signin.*
import javax.inject.Inject

class SignInFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: AppViewModelsFactory
    private lateinit var viewModel: LoginViewModel
    private lateinit var mActivity: Activity

    override fun layoutId(): Int {
        return R.layout.fragment_signin
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_signin, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
        }
    }

    override fun onViewReady(inflatedView: View, args: Bundle?) {
        btn_login.setOnClickListener { onSignInButtonClicked() }
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(requireActivity(), vmFactory).get(LoginViewModel::class.java)

        viewModel.requestStatusLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ApiRequestStatus.RUNNING -> showLoading()
                ApiRequestStatus.SUCCESSFUL -> hideLoading()
                ApiRequestStatus.FAILED -> {
                    // show error it.commonError
                    hideLoading()
                }
            }
        })

//        viewModel.getProfile()
    }

    override fun setListeners() {
    }

    private fun onSignInButtonClicked() {
        viewModel.authorization(et_email.text.toString(), et_password.text.toString()) }
}
