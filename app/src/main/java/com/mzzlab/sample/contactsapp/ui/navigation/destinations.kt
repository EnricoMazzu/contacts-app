package com.mzzlab.sample.contactsapp.ui.navigation

import com.mzzlab.sample.contactsapp.R

object HomeRoute: AppDestination {
    override val route: String = "home"
    override val title: Int = R.string.home_screen_title
    override val requireUpNavigation: Boolean = false
}

fun upNavigationRequired(appDestination: AppDestination): Boolean {
    return appDestination.requireUpNavigation
}

object AppRoutes {

    @JvmStatic
    fun resolveTopLevelRoute(route: String?, fallback: AppDestination = HomeRoute): AppDestination {
        return topLevelRoutes.find {
            it.route == route
        } ?: fallback
    }

    @JvmStatic
    private var topLevelRoutes: List<AppDestination>  = listOf(HomeRoute)
}