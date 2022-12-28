package com.mzzlab.sample.contactsapp.data.impl

import android.content.ContentResolver
import android.provider.ContactsContract
import com.mzzlab.sample.contactsapp.data.ContactsProvider
import com.mzzlab.sample.contactsapp.data.model.Contact
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
            DEFAULT_PROJECTION,
            selection,
            selectionArgs,
            null
        )
        val result: List<Contact>? = cursor.map {
            val nameIndex = it.getColumnIndexOrThrow(ContactsContract.Profile.DISPLAY_NAME_PRIMARY)
            val hasNumber = it.getColumnIndexOrThrow(ContactsContract.Profile.HAS_PHONE_NUMBER)
            Contact(
                name = it.getString(nameIndex),
                hasPhoneNumber = it.getInt(hasNumber) == 1
            )
        }
        cursor?.close()
        return result
    }

    companion object {
        private val DEFAULT_PROJECTION = arrayOf(
            ContactsContract.Contacts._ID,
            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts.CONTACT_LAST_UPDATED_TIMESTAMP,
            ContactsContract.Contacts.LOOKUP_KEY,
            ContactsContract.Contacts.PHOTO_THUMBNAIL_URI
        )
    }

    override suspend fun getContacts(): Contacts = withContext(dispatcher){
        fetchContacts() ?: Collections.emptyList()
    }

}

