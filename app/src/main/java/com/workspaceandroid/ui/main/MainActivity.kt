package com.workspaceandroid.ui.main

import android.os.Bundle
import android.view.MenuItem
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.workspaceandroid.R
import com.workspaceandroid.baseui.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var viewPager: ViewPager
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var mainPagerAdapter: MainPagerAdapter
    private lateinit var mainToolbar: Toolbar
    private var selectedPage = 1;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize components/views.
        viewPager = findViewById(R.id.view_pager);
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        mainPagerAdapter = MainPagerAdapter(supportFragmentManager)

        mainToolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(mainToolbar)

        // Set items to be displayed.
        mainPagerAdapter.setItems(
            arrayListOf(
                MainScreen.LOGS,
                MainScreen.PROGRESS,
                MainScreen.PROFILE
            )
        )

        // Show the default screen.
        val defaultScreen = MainScreen.LOGS
        scrollToScreen(defaultScreen)
        selectBottomNavigationViewMenuItem(defaultScreen.menuItemId)
//        supportActionBar?.setTitle(defaultScreen.titleStringId)

        // Set the listener for item selection in the bottom navigation view.
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        // Attach an adapter to the view pager and make it select the bottom navigation
        // menu item and change the title to proper values when selected.
        viewPager.adapter = mainPagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            private var prevPosition = 0.0f

            override fun onPageSelected(position: Int) {
                val animation1: Animation =
                    AnimationUtils.loadAnimation(applicationContext, R.anim.sliding)
                tv_main_toolbar_title.startAnimation(animation1)
                selectedPage = position;
                val selectedScreen = mainPagerAdapter.getItems()[position]
                selectBottomNavigationViewMenuItem(selectedScreen.menuItemId)
                tv_main_toolbar_title.text = getString(selectedScreen.titleStringId)
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
//                Log.i(
//                    "anim",
//                    "position = " + position + " positionOffset = " + positionOffset + " positionOffsetPixels = " + positionOffsetPixels
//                )
//                //update previous position
//                prevPosition = positionOffset;
//
//                val direction = if (selectedPage < position) -1 else 1
//
////                logoHolder.animate().translationX(logoHolder.getWidth() -positionOffset * logoHolder.getWidth()) .alpha(positionOffset).setDuration(0).start();
//                tv_main_toolbar_title.animate()
//                    .translationX(direction * positionOffset.toFloat())
//                    .alpha(direction * positionOffset)
//                    .setDuration(0)
//                    .start();
            }
        })
    }

    /**
     * Scrolls `ViewPager` to show the provided screen.
     */
    private fun scrollToScreen(mainScreen: MainScreen) {
        val screenPosition = mainPagerAdapter.getItems().indexOf(mainScreen)
        if (screenPosition != viewPager.currentItem) {
            viewPager.currentItem = screenPosition
        }
    }

    /**
     * Selects the specified item in the bottom navigation view.
     */
    private fun selectBottomNavigationViewMenuItem(@IdRes menuItemId: Int) {
        bottomNavigationView.setOnNavigationItemSelectedListener(null)
        bottomNavigationView.selectedItemId = menuItemId
        bottomNavigationView.setOnNavigationItemSelectedListener(this)
    }

    /**
     * Listener implementation for registering bottom navigation clicks.
     */
    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        getMainScreenForMenuItem(menuItem.itemId)?.let {
            scrollToScreen(it)
            tv_main_toolbar_title.text = getString(it.titleStringId)
            return true
        }
        return false
    }

}