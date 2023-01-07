package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mzzlab.sample.contactsapp.data.mock.MockContacts
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.Contacts
import com.mzzlab.sample.contactsapp.ui.theme.ContactsAppTheme
import com.mzzlab.sample.contactsapp.ui.widget.ContactInitial
import com.mzzlab.sample.contactsapp.ui.widget.rememberContactColor
import java.util.*

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(
    modifier: Modifier = Modifier,
    contacts: Map<String, List<Contact>> = Collections.emptyMap(),
    onSelected: (Contact) -> Unit = {}
){
    LazyColumn(modifier) {
        contacts.map { entry ->
            stickyHeader {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, bottom = 5.dp, top = 5.dp))
                {
                    Text(
                        text = entry.key,
                        modifier = Modifier,
                        style = MaterialTheme.typography.h6,
                        color = MaterialTheme.colors.primary
                    )
                }
            }
            items(
                items = entry.value,
                key = { c -> c.id }
            ){ contact ->
                // remember the color derived by the hash of the input string
                val color = rememberContactColor(contactId = contact.id)
                ContactListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelected(contact) }
                        .padding(start = 40.dp, top = 5.dp, end = 5.dp, bottom = 5.dp),
                    contact = contact,
                    color = color,
                    initial = entry.key
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = false, name = "ContactListPreview")
fun ContactListPreview(){
    ContactsAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ContactsList(
                modifier = Modifier.fillMaxSize(),
                contacts = MockContacts.contacts.groupBy { it.name.first().toString() }
            )
        }

    }
}

@Composable
fun ContactListItem(
    modifier: Modifier = Modifier,
    contact: Contact,
    initial: String,
    color: Color = Color.Red,
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        ContactInitial(
            color = color,
            initial = initial,
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = contact.name
        )
    }
}

@Composable
@Preview(showBackground = true, name = "ContactListItemPreview")
fun ContactListItemPreview(){
    val fakeContact = Contact(
        id = "1",
        name = "Adele",
        hasPhoneNumber = true
    )

    ContactListItem(
        Modifier
            .fillMaxWidth(),
        contact = fakeContact,
        initial = "A",
    )
}



