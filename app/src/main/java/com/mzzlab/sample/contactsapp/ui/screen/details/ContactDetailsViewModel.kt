package com.mzzlab.sample.contactsapp.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    val contactsRepository: ContactsRepository
): ViewModel() {

    private lateinit var targetContactId: String

    fun setContactId(contactId: String) {
        this.targetContactId = contactId
    }

    fun reload() {
        viewModelScope.launch {
            val result = contactsRepository.getContactDetails(targetContactId)
            Timber.d("getContactDetails of $targetContactId: $result")
        }
    }
}