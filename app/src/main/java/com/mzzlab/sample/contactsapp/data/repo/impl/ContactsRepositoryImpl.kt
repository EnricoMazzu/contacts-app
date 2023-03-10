package com.mzzlab.sample.contactsapp.data.repo.impl

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.common.MutableAppFlow
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.common.getResult
import com.mzzlab.sample.contactsapp.data.provider.ContactsProvider
import com.mzzlab.sample.contactsapp.data.repo.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ContactsRepositoryImpl(
    private val contactsProvider: ContactsProvider,
) : ContactsRepository {

    private val _contactsFlow: MutableAppFlow<Contacts> =
        MutableStateFlow(Result.Loading(initial = true))

    override val contacts: AppFlow<Contacts> by lazy { _contactsFlow.asStateFlow() }

    override suspend fun reloadContacts() {
        _contactsFlow.value = Result.Loading()
        val result = getResult {
            contactsProvider.getContacts()
        }
        _contactsFlow.value = result
    }

    override suspend fun getContactDetails(
        contactId: String
    ): Result<List<ContactDetails>> {
        return getResult {
            contactsProvider.getContactDetails(contactId)
        }
    }
}