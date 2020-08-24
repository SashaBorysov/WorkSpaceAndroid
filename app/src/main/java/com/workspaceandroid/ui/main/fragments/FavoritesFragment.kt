package com.workspaceandroid.ui.main.fragments


import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.workspaceandroid.AppViewModelsFactory
import com.workspaceandroid.R
import com.workspaceandroid.adapters.AdapterFavoritesItems
import com.workspaceandroid.baseui.BaseFragment
import com.workspaceandroid.data.remote.responses.FavoritesResponse
import com.workspaceandroid.models.network.ApiRequestStatus
import com.workspaceandroid.ui.main.viewModel.MainViewModel
import kotlinx.android.synthetic.main.fragment_favorites.*
import javax.inject.Inject

class FavoritesFragment : BaseFragment() {

    @Inject
    lateinit var vmFactory: AppViewModelsFactory
    private lateinit var viewModel: MainViewModel
    private lateinit var mActivity: Activity

    override fun layoutId(): Int {
        return R.layout.fragment_favorites
    }

    override fun onViewReady(inflatedView: View, args: Bundle?) {

    }

    override fun setListeners() {

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is Activity) {
            mActivity = context
        }
    }

    override fun initViewModel() {
        viewModel = ViewModelProviders.of(requireActivity(), vmFactory).get(MainViewModel::class.java)

        viewModel.requestStatusLiveData.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                ApiRequestStatus.RUNNING -> showLoading()
                ApiRequestStatus.SUCCESSFUL -> {
                    hideLoading()
                }
                ApiRequestStatus.FAILED -> hideLoading()
                ApiRequestStatus.NO_INTERNET -> hideLoading()

            }
        })

        viewModel.favoritesLiveData.observe(viewLifecycleOwner, Observer {
            setUpList(it)
        })

        viewModel.getWords()
    }

    private fun setUpList(favoritesData: List<FavoritesResponse>) {
        // Creates a vertical Layout Manager
        rv_favorites_container.layoutManager = LinearLayoutManager(mActivity)
        val favoritesAdapter = AdapterFavoritesItems(favoritesData, mActivity)
        rv_favorites_container.adapter = favoritesAdapter
    }
}
