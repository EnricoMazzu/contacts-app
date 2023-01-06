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

    override suspend fun getContacts(): Contacts = withContext(dispatcher){
        fetchContacts() ?: Collections.emptyList()
    }

    override suspend fun getContactDetails(contactId: String): List<ContactDetails> = withContext(dispatcher) {
        fetchContactData(contactId) ?: Collections.emptyList()
    }

    private fun query(
        uri: Uri,
        projection: Array<String>,
        selection: String? = null,
        selectionArgs: Array<String>? = null,
        sort: String? = null
    ): Cursor? {
        return contentResolver.query(uri, projection, selection, selectionArgs, sort)
    }

    private fun fetchContacts(): List<Contact>? {
        val cursor = query(
            uri = ContactsContract.Contacts.CONTENT_URI,
            projection = CONTACT_PROJECTION,
            sort = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY
        )
        val result: List<Contact>? = cursor.map { it.asContact() }
        cursor?.close()
        return result
    }

    private fun fetchContactData(contactId: String): List<ContactDetails>? {
        val cursor: Cursor? = query(
            uri = ContactsContract.Data.CONTENT_URI,
            projection = CONTACT_DATA_PROJECTION,
            selection = CONTACT_DETAILS_SELECT, // contact_id = ? AND mimetype IN (...)
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

    /*private fun tryRawData(contactId: String) {
        val cursor: Cursor? = query(
            uri = ContactsContract.RawContacts.CONTENT_URI,
            projection = arrayOf(
                ContactsContract.RawContacts._ID,
                ContactsContract.RawContacts.CONTACT_ID,
                ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY,
                ContactsContract.RawContacts.RAW_CONTACT_IS_USER_PROFILE,
                ContactsContract.RawContacts.ACCOUNT_NAME,
                ContactsContract.RawContacts.ACCOUNT_TYPE,
                ContactsContract.RawContacts.ACCOUNT_TYPE_AND_DATA_SET,
            ),
            selection = "${ContactsContract.RawContacts.CONTACT_ID}=?", // contact_id = ? AND mimetype IN (...)
            selectionArgs = arrayOf(
                contactId,
            )
        )
        Timber.d(">>> Count: ${cursor?.count ?: -1}")
        cursor.map {
            it.columnNames.map { col ->
                val value = it.optStringByName(col)
                Pair(col, value)
            }
        }?.forEach {
            Timber.d(">>> Pair: $it")
        }
    }*/

    companion object {

        @JvmStatic
        private val CONTACT_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
        )

        @JvmStatic
        private val CONTACT_DATA_PROJECTION = arrayOf(
            ContactsContract.Data._ID,
            ContactsContract.Data.DISPLAY_NAME_PRIMARY,
            ContactsContract.Data.CONTACT_ID,
            ContactsContract.Data.RAW_CONTACT_ID,
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

