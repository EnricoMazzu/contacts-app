package com.mzzlab.sample.contactsapp.ui.navigation

import com.mzzlab.sample.contactsapp.R

object NavigationConstants {
    const val NoId = "NO_ID"
}

object HomeRoute: AppDestination {
    override val route: String = "home"
    override val title: Int = R.string.home_screen_title
    override val requireUpNavigation: Boolean = false
}

object ContactDetailsRoute: AppDestination {
    const val CONTACT_ID_PARAMS = "contactId"
    private const val baseRoute = "contacts"
    override val route: String = "$baseRoute/{$CONTACT_ID_PARAMS}"
    override val title: Int = R.string.details_screen_title
    override val requireUpNavigation: Boolean = true

    fun createRoute(contactId: String) = "$baseRoute/$contactId"
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
    private var topLevelRoutes: List<AppDestination>  = listOf(HomeRoute, ContactDetailsRoute)
}