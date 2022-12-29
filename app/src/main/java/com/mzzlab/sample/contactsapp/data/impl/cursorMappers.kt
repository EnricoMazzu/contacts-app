package com.mzzlab.sample.contactsapp.data.impl

import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.Email
import android.provider.ContactsContract.CommonDataKinds.Phone
import android.provider.ContactsContract.CommonDataKinds.Photo
import android.provider.ContactsContract.CommonDataKinds.StructuredName
import android.provider.ContactsContract.CommonDataKinds.Website
import android.provider.ContactsContract.Data
import android.provider.ContactsContract.Profile
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import timber.log.Timber

fun Cursor.asContact(): Contact {
    return Contact(
        id = getStringByName(ContactsContract.Contacts._ID),
        name = getStringByName(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY),
        hasPhoneNumber = getIntByName(ContactsContract.Contacts.HAS_PHONE_NUMBER) == 1
    )
}

fun Cursor.asContactDetails(): ContactDetails {
    val id = getStringByName(ContactsContract.Data._ID)
    val displayName = getStringByName(ContactsContract.Data.DISPLAY_NAME_PRIMARY)
    val contactId = getStringByName(ContactsContract.Data.CONTACT_ID)
    val mimeType = getStringByName(ContactsContract.Data.MIMETYPE)
    Timber.d(">>> id:$id mimeType: $mimeType")
    return when(mimeType){
        StructuredName.CONTENT_ITEM_TYPE -> ContactDetails.Name(
            id = id,
            contactId = contactId,
            displayName = displayName
        )
        Phone.CONTENT_ITEM_TYPE -> ContactDetails.Phone(
            id = id,
            contactId = contactId,
            displayName = displayName,
            phoneType = Phone.getTypeLabelResource(optIntByName(Phone.TYPE)),
            phoneNumber = optStringByName(Phone.NUMBER),
            phoneLabel = optStringByName(Phone.LABEL)
        )
        Email.CONTENT_ITEM_TYPE -> ContactDetails.Email(
            id = id,
            contactId = contactId,
            displayName = displayName,
            email = optStringByName(Email.ADDRESS)
        )
        Website.CONTENT_ITEM_TYPE -> ContactDetails.Website(
            id = id,
            contactId = contactId,
            displayName = displayName,
            url = optStringByName(Website.URL)
        )
        else -> ContactDetails.Other(
            id = id,
            contactId = contactId,
            displayName = displayName,
            data1 = optStringByName(Data.DATA1),
            data2 = optStringByName(Data.DATA2),
            data3 = optStringByName(Data.DATA3)
        )
    }

}