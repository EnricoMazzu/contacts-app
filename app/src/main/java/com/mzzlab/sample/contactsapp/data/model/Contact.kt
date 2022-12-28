package com.mzzlab.sample.contactsapp.data.model

data class Contact(
    val name: String?,
    val hasPhoneNumber: Boolean = false
)

typealias Contacts = List<Contact>