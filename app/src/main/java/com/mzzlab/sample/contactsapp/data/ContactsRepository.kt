package com.mzzlab.sample.contactsapp.data

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts

interface ContactsRepository {

    val contacts: AppFlow<Contacts>

    suspend fun reloadContacts()

    suspend fun getContactDetails(contactId: String): com.mzzlab.sample.contactsapp.common.Result<List<ContactDetails>>
}