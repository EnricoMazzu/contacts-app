package com.mzzlab.sample.contactsapp.data

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.data.model.Contacts

interface ContactsRepository {

    suspend fun getContacts(): AppFlow<Contacts>

    suspend fun reloadContacts()
}