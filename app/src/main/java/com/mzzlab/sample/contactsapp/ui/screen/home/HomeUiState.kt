package com.mzzlab.sample.contactsapp.ui.screen.home

import com.mzzlab.sample.contactsapp.data.model.Contact
import java.util.Collections

data class HomeUiState(
    val loading: Boolean = false,
    val refreshing: Boolean = false,
    val contacts: GroupedContacts = Collections.emptyMap()
)

typealias GroupedContacts = Map<String, List<Contact>>