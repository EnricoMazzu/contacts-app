package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.mzzlab.sample.contactsapp.R
import com.mzzlab.sample.contactsapp.ui.navigation.HomeRoute
import com.mzzlab.sample.contactsapp.ui.widget.PermissionAwareContainer

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    PermissionAwareContainer(
        permission = android.Manifest.permission.READ_CONTACTS,
        rationaleMsg = R.string.contact_permission_rationale,
        permissionDesc = R.string.contact_permission_desc,
    ) {
        HomeContent(state)
    }
}

@Composable
fun HomeContent(state: HomeUiState) {
    if(state.loading){
        Text("Loading")
    }else{
       ContactsList(
           modifier = Modifier,
           contacts = state.contacts
       )
    }
}


fun NavGraphBuilder.addHomeRoute(
    navController: NavHostController
) {
    composable(route = HomeRoute.route) {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel)
    }
}