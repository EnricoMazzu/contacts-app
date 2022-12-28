package com.mzzlab.sample.contactsapp.data.impl

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.common.MutableAppFlow
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.common.getResult
import com.mzzlab.sample.contactsapp.data.ContactsProvider
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.Contacts
import kotlinx.coroutines.flow.flow


class ContactsRepositoryImpl (
    val contactsProvider: ContactsProvider
): ContactsRepository {


    override suspend fun getContacts(): AppFlow<Contacts>  {
        return flow {
            emit(Result.Loading())
            val next = getResult {
                contactsProvider.getContacts()
            }
            emit(next)
        }
    }

    override suspend fun reloadContacts() {
        TODO("Not yet implemented")
    }

    /*
    private suspend fun fetchContacts(flow: MutableAppFlow<Contacts>){

    }
    */

}