package com.mzzlab.sample.contactsapp.data.mock

import com.mzzlab.sample.contactsapp.common.AppFlow
import com.mzzlab.sample.contactsapp.common.MutableAppFlow
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.data.repo.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.data.model.Contacts
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import java.util.*

class MockContactsRepository(
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): ContactsRepository {

    private val _contacts: MutableAppFlow<Contacts> = MutableStateFlow(Result.Loading(true))

    override val contacts: AppFlow<Contacts>
        get() = _contacts.asStateFlow()

    override suspend fun reloadContacts() = withContext(dispatcher) {
        _contacts.value = Result.Loading()
        delay(1000)
        _contacts.value = Result.Success(MockContacts.contacts)
    }

    override suspend fun getContactDetails(contactId: String): Result<List<ContactDetails>> = withContext(dispatcher) {
        delay(200)
        val contact: Contact? = MockContacts.contacts.find { c -> contactId == c.id }
        val data = contact?.let {
            listOf<ContactDetails>(
                ContactDetails.Name("1", contactId = contact.id, accountType = "com.google" , displayName = contact.name)
            )
        } ?: Collections.emptyList()
        Result.Success(data)
    }
}