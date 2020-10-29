package com.workspaceandroid.ui.main

import androidx.annotation.DrawableRes
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.workspaceandroid.R
import com.workspaceandroid.ui.main.fragments.AccountFragment
import com.workspaceandroid.ui.main.fragments.FavoritesFragment
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
    LOGS(R.id.navigation_home, R.drawable.ic_home_black_24dp, R.string.title_home, FavoritesFragment()),
    PROGRESS(R.id.navigation_dashboard, R.drawable.ic_home_black_24dp, R.string.title_dashboard, HomeFragment()),
    PROFILE(R.id.navigation_notifications, R.drawable.ic_home_black_24dp, R.string.title_notifications, AccountFragment())
}

fun getMainScreenForMenuItem(menuItemId: Int): MainScreen? {
    for (mainScreen in MainScreen.values()) {
        if (mainScreen.menuItemId == menuItemId) {
            return mainScreen
        }
    }
    return null
}