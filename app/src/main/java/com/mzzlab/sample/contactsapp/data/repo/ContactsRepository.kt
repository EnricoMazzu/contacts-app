package com.mzzlab.sample.contactsapp.data.repo

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts

/**
 * Repository for contacts
 */
interface ContactsRepository {

    /**
     * The loaded contacts
     */
    val contacts: AppFlow<Contacts>

    /**
     * Reload the contacts from the content provider
     */
    suspend fun reloadContacts()

    /**
     * Read details of our specific contacts
     */
    suspend fun getContactDetails(contactId: String): com.mzzlab.sample.contactsapp.common.Result<List<ContactDetails>>
}