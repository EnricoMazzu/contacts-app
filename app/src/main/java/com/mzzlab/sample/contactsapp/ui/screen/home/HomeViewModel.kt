package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.sample.contactsapp.common.switch
import com.mzzlab.sample.contactsapp.data.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
): ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            contactsRepository
                .contacts
                .collect { result ->
                    Timber.d("Result: $result")
                    result.switch(
                        loading = { _uiState.update { it.copy(loading = true) } },
                        success = { data ->  _uiState.update { it.copy(loading = false, contacts = data.orEmpty()) }},
                        error = { err -> Timber.e(err, "Error on getContacts")}
                    )
                }

        }
    }
}