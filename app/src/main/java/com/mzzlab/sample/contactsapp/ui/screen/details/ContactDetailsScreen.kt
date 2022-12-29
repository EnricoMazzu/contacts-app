package com.mzzlab.sample.contactsapp.ui.screen.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.mzzlab.sample.contactsapp.ui.navigation.ContactDetailsRoute
import com.mzzlab.sample.contactsapp.ui.navigation.NavigationConstants

@Composable
fun ContactDetailsScreen(viewModel: ContactDetailsViewModel) {
    LaunchedEffect(Unit){
        viewModel.reload()
    }
}


fun NavGraphBuilder.addContactDetailsScreen(
    navController: NavHostController
){
    composable(
        ContactDetailsRoute.route,
        arguments = listOf(
            navArgument(ContactDetailsRoute.CONTACT_ID_PARAMS){
                nullable = false
                type = NavType.StringType
            }
        )
    ){
        val contactId = it.arguments?.getString(ContactDetailsRoute.CONTACT_ID_PARAMS)
            ?: NavigationConstants.NoId
        val viewModel: ContactDetailsViewModel = hiltViewModel()
        viewModel.setContactId(contactId)
        ContactDetailsScreen(viewModel = viewModel)
    }
}