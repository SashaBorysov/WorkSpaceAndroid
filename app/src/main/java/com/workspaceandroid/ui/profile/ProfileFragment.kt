package com.workspaceandroid.ui.profile

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseFragment

class ProfileFragment : BaseFragment() {

    private lateinit var viewModel: ProfileViewModel

    override fun layoutId(): Int {
        return R.layout.fragment_profile
    }

    override fun onViewReady(inflatedView: View, args: Bundle?) {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }

    override fun setListeners() {
        TODO("Not yet implemented")
    }


}