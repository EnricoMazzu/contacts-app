package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.lifecycle.ViewModel
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    val contactsRepository: ContactsRepository
): ViewModel() {

}