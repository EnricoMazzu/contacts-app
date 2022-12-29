package com.mzzlab.sample.contactsapp.data.impl

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.common.MutableAppFlow
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.common.getResult
import com.mzzlab.sample.contactsapp.data.ContactsProvider
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.Contacts
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow


class ContactsRepositoryImpl (
    private val contactsProvider: ContactsProvider,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ContactsRepository {

    private val _contactsFlow: MutableAppFlow<Contacts> = MutableStateFlow(Result.Loading(true))

    private fun shouldBeReloaded(value: Result<List<Contact>>): Boolean {
        return (value as? Result.Loading)?.initial ?: false
    }

    override val contacts: AppFlow<Contacts> by lazy {
        initialize()
        _contactsFlow.asStateFlow()
    }

    private fun initialize() {
        CoroutineScope(dispatcher + SupervisorJob()).launch {
            reloadContacts()
        }
    }

    override suspend fun reloadContacts() {
        _contactsFlow.value = Result.Loading()
        val result = getResult {
            contactsProvider.getContacts()
        }
        _contactsFlow.value = result
    }

}