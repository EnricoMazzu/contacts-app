package com.mzzlab.sample.contactsapp.ui.widget

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.mzzlab.sample.contactsapp.ui.navigation.AppDestination
import com.mzzlab.sample.contactsapp.ui.navigation.upNavigationRequired

@Composable
fun ContactsTopAppBar(
    appDestination: AppDestination,
    onUpNavigation: () -> Unit = {}
) {
    val showUpNavigationBack = upNavigationRequired(appDestination)
    TopAppBar(
        title = { Text(stringResource(id = appDestination.title)) },
        navigationIcon = if (showUpNavigationBack) {
            {
                IconButton(onClick = onUpNavigation) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "BackIcon",
                    )
                }
            }
        } else null,
    )
}