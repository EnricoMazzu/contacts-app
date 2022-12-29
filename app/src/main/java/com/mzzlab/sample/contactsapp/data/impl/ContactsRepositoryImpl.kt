package com.mzzlab.sample.contactsapp.data.impl

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.common.MutableAppFlow
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.common.getResult
import com.mzzlab.sample.contactsapp.data.ContactsProvider
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.Contacts
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactsRepositoryImpl (
    private val contactsProvider: ContactsProvider,
): ContactsRepository {

    private val _contactsFlow: MutableAppFlow<Contacts> = MutableStateFlow(Result.Loading(true))

    override val contacts: AppFlow<Contacts> by lazy {
        _contactsFlow.asStateFlow()
    }

    override suspend fun reloadContacts() {
        _contactsFlow.value = Result.Loading()
        delay(3000)
        val result = getResult {
            contactsProvider.getContacts()
        }
        _contactsFlow.value = result
    }
}