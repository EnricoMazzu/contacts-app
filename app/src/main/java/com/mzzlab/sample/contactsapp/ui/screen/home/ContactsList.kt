package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.Contacts
import java.util.Collections

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ContactsList(
    modifier: Modifier = Modifier,
    contacts: Contacts = Collections.emptyList(),
    onSelected: (Contact) -> Unit = {}
){
    val mapped = remember(contacts) { contacts.groupBy { it.name.first().toString() } }
    LazyColumn(modifier) {
        mapped.map { entry ->
            stickyHeader {
                Column(Modifier
                    .fillMaxWidth()
                    .padding(PaddingValues(start = 5.dp, bottom = 5.dp)))
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
                ContactListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelected(contact) }
                        .padding(PaddingValues(start = 40.dp, top = 5.dp, end = 5.dp, bottom = 5.dp)),
                    contact = contact,
                    initial = entry.key
                )
            }
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
            initial = initial
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = contact.name
        )
    }

}

@Composable
private fun ContactInitial(
    modifier: Modifier = Modifier,
    color: Color,
    initial: String,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
) {
    Box(modifier.size(40.dp), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        Text(text = initial, style = textStyle, color = Color.White)
    }
}


@Composable
@Preview(showBackground = false)
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



