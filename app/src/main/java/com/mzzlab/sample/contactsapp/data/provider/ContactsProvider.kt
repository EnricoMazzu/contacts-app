package com.mzzlab.sample.contactsapp.data.provider

import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts

/**
 * Provider for the contacts
 */
interface ContactsProvider {

    /**
     * Fetch all the contacts
     */
    suspend fun getContacts(): Contacts

    /**
     * Read the details of a contact
     *
     * @param contactId the contact id
     */
    suspend fun getContactDetails(contactId: String): List<ContactDetails>

}