package com.mzzlab.sample.contactsapp.ui.navigation

sealed interface AppDestination {
    val route: String
    val title: Int
    val requireUpNavigation: Boolean
}