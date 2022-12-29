package com.mzzlab.sample.contactsapp.data.model

data class Contact(
    val id: String,
    val name: String,
    val hasPhoneNumber: Boolean = false
)

typealias Contacts = List<Contact>