package com.mzzlab.sample.contactsapp.data

import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts

/**
 * Provider for the contacts
 */
interface ContactsProvider {

    /**
     * Fetch all contacts
     *
     * @param selection the where clause
     * @param selectionArgs the where clause arguments
     */
    suspend fun getContacts(
        selection: String? = null,
        selectionArgs: Array<String>? = null
    ): Contacts

    /**
     * Read the details of a contact
     *
     * @param contactId the contact id
     */
    suspend fun getContactDetails(contactId: String): List<ContactDetails>

}