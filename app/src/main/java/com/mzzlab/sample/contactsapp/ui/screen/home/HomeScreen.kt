package com.mzzlab.sample.contactsapp.ui.screen.home

import android.Manifest
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.mzzlab.sample.contactsapp.R
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.ui.navigation.ContactDetailsRoute
import com.mzzlab.sample.contactsapp.ui.navigation.HomeRoute
import com.mzzlab.sample.contactsapp.ui.widget.PermissionBox
import timber.log.Timber

@OptIn(
    ExperimentalLifecycleComposeApi::class,
    ExperimentalMaterialApi::class,
    ExperimentalPermissionsApi::class
)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onContactSelected: (Contact) -> Unit = {}
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    Timber.d("new State: $state")
    val pullRefreshState = rememberPullRefreshState(state.refreshing, {
        viewModel.refreshContacts(true)
    })
    val permissionState = rememberPermissionState(Manifest.permission.READ_CONTACTS)

    if (!permissionState.status.isGranted) {
        PermissionBox(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            permissionState = permissionState,
            rationaleMessage = R.string.contact_permission_rationale,
            permissionDesc = R.string.contact_permission_desc
        )
    } else {
        LaunchedEffect(permissionState, permissionState.status){
            viewModel.refreshContacts()
        }
        HomeContent(
            state = state,
            pullRefreshState = pullRefreshState,
            onContactSelected = onContactSelected
        )
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeUiState,
    pullRefreshState: PullRefreshState,
    onContactSelected: (Contact) -> Unit = {}
) {
    Box(modifier = modifier
        .fillMaxSize()
        .pullRefresh(pullRefreshState)
    ) {
        AnimatedVisibility(visible = state.loading && !state.refreshing) {
            LinearProgressIndicator(
                Modifier.fillMaxWidth()
            )
        }
        ContactsList(
            modifier = Modifier.fillMaxSize(),
            contacts = state.contacts,
            onSelected = onContactSelected
        )
        PullRefreshIndicator(state.refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
    }
}

fun NavGraphBuilder.addHomeRoute(
    navController: NavHostController
) {
    composable(route = HomeRoute.route) {
        val viewModel: HomeViewModel = hiltViewModel()
        HomeScreen(viewModel){
            Timber.d("Navigate to $it")
            navController.navigate(ContactDetailsRoute.createRoute(it.id))
        }
    }
}