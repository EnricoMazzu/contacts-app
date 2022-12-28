package com.mzzlab.sample.contactsapp.data

import com.mzzlab.sample.contactsapp.data.model.Contacts

interface ContactsProvider {
    suspend fun getContacts(): Contacts
}