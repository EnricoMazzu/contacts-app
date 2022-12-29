package com.mzzlab.sample.contactsapp.data

import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts

interface ContactsProvider {
    /**
     *
     */
    suspend fun getContacts(): Contacts

    /**
     *
     * @param contactId
     */
    suspend fun getContactDetails(contactId: String): List<ContactDetails>

}