package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mzzlab.sample.contactsapp.ui.navigation.HomeRoute
import com.mzzlab.sample.contactsapp.ui.theme.ContactsAppTheme

@Composable
fun HomeScreen(viewModel: HomeViewModel){
    Text(text = "Home Screen")
}


@Preview(showBackground = false)
@Composable
fun DefaultPreview(){
    ContactsAppTheme {
        HomeScreen(viewModel = hiltViewModel())
    }
}


fun NavGraphBuilder.addHomeRoute(
    navController: NavHostController
) {
    composable(route = HomeRoute.route){
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel = viewModel)
    }
}