package com.mzzlab.sample.contactsapp.ui.screen.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.data.repo.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.Contact
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
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
                    processResult(result)
                }
        }
    }

    private fun processResult(result: Result<List<Contact>>) {
        Timber.d("Result: $result")
        when(result) {
            is Result.Loading -> _uiState.update { it.copy(loading = true) }
            is Result.Success -> {
                _uiState.update {
                    it.copy(
                        loading = false,
                        refreshing = false,
                        contacts = result.data.orEmpty()
                    )
                }
            }
            is Result.Error -> { //TODO handle error here
                Timber.e(result.exception, "processResult error")
            }
        }
    }

    fun refreshContacts(pullToRefresh: Boolean = false) {
        Timber.d("refreshContacts")
        viewModelScope.launch {
            _uiState.update { it.copy(refreshing = pullToRefresh) }
            delay(200)
            contactsRepository.reloadContacts()
        }
    }

}