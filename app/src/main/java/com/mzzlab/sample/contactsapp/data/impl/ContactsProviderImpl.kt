package com.mzzlab.sample.contactsapp.data.impl

import android.content.ContentResolver
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.RawContacts
import com.mzzlab.sample.contactsapp.data.ContactsProvider
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class ContactsProviderImpl(
    private val contentResolver: ContentResolver,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ContactsProvider {

    private fun fetchContacts(
        selection: String? = null,
        selectionArgs: Array<String>? = null
    ): List<Contact>? {
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            CONTACT_PROJECTION,
            selection,
            selectionArgs,
            CONTACT_SORT
        )
        val result: List<Contact>? = cursor.map { it.asContact() }
        cursor?.close()
        return result
    }


    override suspend fun getContacts(): Contacts = withContext(dispatcher){
        fetchContacts() ?: Collections.emptyList()
    }

    override suspend fun getContactDetails(contactId: String): List<ContactDetails> = withContext(dispatcher) {
        fetchContactDetails(contactId) ?: Collections.emptyList()
    }

    private fun fetchContactDetails(contactId: String): List<ContactDetails>? {
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            CONTACT_DATA_PROJECTION,
            ContactsContract.Data.CONTACT_ID + "=?",
            arrayOf(java.lang.String.valueOf(contactId)),
            null
        )
        val result: List<ContactDetails>? = cursor?.map { it.asContactDetails()}
        cursor?.close()
        return result
    }


    companion object {
        private val CONTACT_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
        )
        private const val CONTACT_SORT = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY

        private val CONTACT_DATA_PROJECTION = arrayOf(
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.MIMETYPE,
            //Email.DATA,
            //Phone.NUMBER,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.DATA3,
        )
    }

}

