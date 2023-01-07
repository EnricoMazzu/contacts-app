package com.mzzlab.sample.contactsapp.data.provider.impl

import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.*
import com.mzzlab.sample.contactsapp.data.map
import com.mzzlab.sample.contactsapp.data.mapper.asContact
import com.mzzlab.sample.contactsapp.data.mapper.asContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts
import com.mzzlab.sample.contactsapp.data.provider.ContactsProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.*


class ContactsProviderImpl(
    private val contentResolver: ContentResolver,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ContactsProvider {

    private fun query(
        uri: Uri,
        projection: Array<String>,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sort: String? = null
    ): Cursor? {
        return contentResolver.query(uri, projection, selection, selectionArgs, sort)
    }

    override suspend fun getContacts(): Contacts = withContext(dispatcher){
        fetchContacts() ?: Collections.emptyList()
    }

    private fun fetchContacts(): List<Contact>? {
        val cursor = query(
            uri = ContactsContract.Contacts.CONTENT_URI,
            projection = CONTACTS_PROJECTION,
            sort = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )
        val result: List<Contact>? = cursor.map { it.asContact() }
        cursor?.close()
        return result
    }

    override suspend fun getContactDetails(contactId: String): List<ContactDetails> = withContext(dispatcher) {
        fetchContactData(contactId) ?: Collections.emptyList()
    }

    private fun fetchContactData(contactId: String): List<ContactDetails>? {
        val cursor: Cursor? = query(
            uri = ContactsContract.Data.CONTENT_URI,
            projection = CONTACT_DATA_PROJECTION,
            //selection: contact_id = ? AND mimetype IN (?,?,?,?)
            selection = CONTACT_DETAILS_SELECT,
            selectionArgs = arrayOf(
                contactId,
                StructuredName.CONTENT_ITEM_TYPE,
                Phone.CONTENT_ITEM_TYPE,
                Email.CONTENT_ITEM_TYPE,
                Website.CONTENT_ITEM_TYPE
            )
        )
        val result: List<ContactDetails>? = cursor?.map { it.asContactDetails() }
        cursor?.close()
        return result
    }

    companion object {

        @JvmStatic
        private val CONTACTS_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
        )

        @JvmStatic
        private val CONTACT_DATA_PROJECTION = arrayOf(
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.ACCOUNT_TYPE_AND_DATA_SET,
            ContactsContract.Data.MIMETYPE,
            ContactsContract.Data.DATA1,
            ContactsContract.Data.DATA2,
            ContactsContract.Data.DATA3,
        )

        private const val CONTACT_DETAILS_SELECT = "${ContactsContract.Data.CONTACT_ID}=?" +
                " AND ${ContactsContract.Data.MIMETYPE} IN (?,?,?,?)"

    }

}

