package com.mzzlab.sample.contactsapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mzzlab.sample.contactsapp.ui.navigation.AppDestination
import com.mzzlab.sample.contactsapp.ui.navigation.AppNavHost
import com.mzzlab.sample.contactsapp.ui.navigation.AppRoutes
import com.mzzlab.sample.contactsapp.ui.navigation.HomeRoute
import com.mzzlab.sample.contactsapp.ui.theme.ContactsAppTheme
import com.mzzlab.sample.contactsapp.ui.widget.ContactsTopAppBar

@Composable
fun App(){
    ContactsAppTheme {
        val appState = rememberAppState()
        val currentBackStack by appState.navController.currentBackStackEntryAsState()
        val appDestination = resolveAppDestination(currentBackStack)
        Scaffold(
            scaffoldState = appState.scaffoldState,
            topBar = {
                ContactsTopAppBar(
                    appDestination = appDestination,
                    onUpNavigation = {
                        appState.navController.popBackStack()
                    }
                )
            }
        ) {
            AppNavHost(
                modifier = Modifier.padding(it),
                navController = appState.navController
            )
        }
    }
}

fun resolveAppDestination(currentBackStack: NavBackStackEntry?): AppDestination {
    return AppRoutes.resolveTopLevelRoute(
        route = currentBackStack?.destination?.route,
        fallback = HomeRoute
    )
}
