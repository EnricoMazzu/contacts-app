package com.mzzlab.sample.contactsapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.mzzlab.sample.contactsapp.ui.screen.home.addHomeRoute

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = HomeRoute.route
){
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
    ){
        addHomeRoute(navController)
    }
}