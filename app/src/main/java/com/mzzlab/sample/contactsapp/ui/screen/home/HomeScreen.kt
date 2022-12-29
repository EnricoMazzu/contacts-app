package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mzzlab.sample.contactsapp.R
import com.mzzlab.sample.contactsapp.ui.navigation.HomeRoute
import com.mzzlab.sample.contactsapp.ui.widget.PermissionAwareContainer

@Composable
fun HomeScreen(
    viewModel: HomeViewModel) {
    PermissionAwareContainer(
        permission = android.Manifest.permission.READ_CONTACTS,
        rationaleMsg = R.string.contact_permission_rationale,
        permissionDesc = R.string.contact_permission_desc,
    ) {
        HomeContent()
    }
}

@Composable
fun HomeContent() {

}


fun NavGraphBuilder.addHomeRoute(
    navController: NavHostController
) {
    composable(route = HomeRoute.route) {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel)
    }
}