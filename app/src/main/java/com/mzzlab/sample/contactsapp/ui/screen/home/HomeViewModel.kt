package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.data.model.Contact
import com.mzzlab.sample.contactsapp.data.repo.ContactsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState(loading = true))
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            contactsRepository
                .contacts
                .collect { result ->
                    processResult(result)
                }
        }
    }

    private suspend fun processResult(result: Result<List<Contact>>) {
        Timber.d("Process result: $result")
        when (result) {
            is Result.Loading -> _uiState.update { it.copy(loading = true) }
            is Result.Success -> {
                val groupedByInitial = groupContacts(result.data ?: Collections.emptyList())
                _uiState.update {
                    it.copy(
                        loading = false,
                        refreshing = false,
                        contacts = groupedByInitial
                    )
                }
            }
            is Result.Error -> {
                Timber.e(result.exception, "processResult error")
                //TODO handle error here
            }
        }
    }

    private suspend fun groupContacts(contacts: List<Contact>): GroupedContacts {
        return withContext(Dispatchers.IO) {
            contacts.groupBy { it.name.first().toString() }
        }
    }

    fun refreshContacts(pullToRefresh: Boolean = false) {
        Timber.d("refreshContacts pullToRefresh: $pullToRefresh")
        viewModelScope.launch {
            _uiState.update { it.copy(refreshing = pullToRefresh) }
            delay(200)
            contactsRepository.reloadContacts()
        }
    }

}