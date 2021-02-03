package com.workspaceandroid.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.workspaceandroid.R
import com.workspaceandroid.ui.main.fragments.SettingsFragment
import com.workspaceandroid.ui.main.fragments.SearchFragment
import com.workspaceandroid.ui.main.fragments.HomeFragment

/**
 * Screens available for display in the main screen, with their respective titles,
 * icons, and menu item IDs and fragments.
 */
enum class MainScreen(@IdRes val menuItemId: Int,
                      @DrawableRes val menuItemIconId: Int,
                      @StringRes val titleStringId: Int,
                      val fragment: Fragment
) {
    SEARCH(R.id.navigation_search, R.drawable.ic_bottom_nav_search, R.string.title_bottom_nav_search, SearchFragment()),
    HOME(R.id.navigation_home, R.drawable.ic_bottom_nav_home, R.string.title_bottom_nav_home, HomeFragment()),
    SETTING(R.id.navigation_settings, R.drawable.ic_bottom_nav_settings, R.string.title_bottom_nav_settings, SettingsFragment())
}

fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}