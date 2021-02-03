package com.workspaceandroid.ui.main.fragments


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseFragment
import com.workspaceandroid.ui.main.MainActivity
import com.workspaceandroid.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.fragment_settings.*

/**
 * A simple [Fragment] subclass.
 */
class SettingsFragment : BaseFragment() {

    private lateinit var mParentActivity: MainActivity

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            mParentActivity = context as MainActivity
        } catch (e: ClassCastException) {
            throw ClassCastException(
                this.javaClass.name + " ClassCastException"
            )
        }
    }

    override fun layoutId(): Int {
        return R.layout.fragment_settings
    }

    override fun onViewReady(inflatedView: View, args: Bundle?) {
        updateUI()
        showLoading()
    }

    override fun initViewModel() {

    }

    override fun setListeners() {
        ll_profile_container.setOnClickListener{
            val transaction = mParentActivity.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.view_pager, ProfileFragment())
            transaction.commit()
        }
    }

    private fun updateUI() {

    }

}
