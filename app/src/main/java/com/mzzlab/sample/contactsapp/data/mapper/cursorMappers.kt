package com.mzzlab.sample.contactsapp.data.mapper

import android.database.Cursor
import android.provider.ContactsContract
import android.provider.ContactsContract.CommonDataKinds.*
import android.provider.ContactsContract.Data
import com.mzzlab.sample.contactsapp.data.impl.getIntByName
import com.mzzlab.sample.contactsapp.data.impl.getStringByName
import com.mzzlab.sample.contactsapp.data.impl.optIntByName
import com.mzzlab.sample.contactsapp.data.impl.optStringByName
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
    val id = getStringByName(Data._ID)
    val displayName = getStringByName(Data.DISPLAY_NAME_PRIMARY)
    val contactId = getStringByName(Data.CONTACT_ID)
    val mimeType = getStringByName(Data.MIMETYPE)
    Timber.d("id: $id , mimeType: $mimeType")
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