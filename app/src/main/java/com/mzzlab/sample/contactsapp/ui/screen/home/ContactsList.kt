package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.Contacts
import java.util.Collections

@Composable
fun ContactsList(
    modifier: Modifier = Modifier,
    contacts: Contacts = Collections.emptyList(),
    onSelected: (Contact) -> Unit = {}
){
    LazyColumn(modifier) {
        items(
            items = contacts,
            key = { c -> c.id }
        ){ contact ->
            ContactListItem(contact = contact, onSelected = onSelected)
        }
    }
}

@Composable
fun ContactListItem(
    contact: Contact,
    onSelected: (Contact) -> Unit = {}
) {
    Text(text = "${contact.id} - ${contact.name}")
}