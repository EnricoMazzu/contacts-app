package com.mzzlab.sample.contactsapp.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import com.mzzlab.sample.contactsapp.ui.theme.ContactsAppTheme

@Composable
fun App(){
    ContactsAppTheme {
        val appState = rememberAppState()
        val currentBackStack by appState.navController.currentBackStackEntryAsState()
        Text(text = "Hello !!!")
    }
}