package com.mzzlab.sample.contactsapp.ui.screen.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mzzlab.sample.contactsapp.R
import com.mzzlab.sample.contactsapp.common.Result
import com.mzzlab.sample.contactsapp.data.repo.ContactsRepository
import com.mzzlab.sample.contactsapp.data.model.ContactDetails
import com.mzzlab.sample.contactsapp.ui.widget.UiError
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ContactDetailsViewModel @Inject constructor(
    private val contactsRepository: ContactsRepository
): ViewModel() {

    private lateinit var _contactId: String
    val contactId: String by lazy { _contactId }

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    fun setTargetContactId(contactId: String){
        this._contactId = contactId
    }

    fun loadDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            val result = contactsRepository.getContactDetails(_contactId)
            Timber.d("getContactDetails of $_contactId: $result")
            when(result){
                is Result.Loading -> { Timber.d("loading...")}
                is Result.Success -> { processDetails(result.data) }
                is Result.Error ->  { showError(result.exception) }
            }
        }
    }

    private fun showError(th: Throwable?) {
        th?.let { ex ->
            _uiState.update { it.copy(error = createUiError(ex)) }
        }
    }

    private fun createUiError(ex: Throwable): UiError {
        return UiError(ex.message.orEmpty())
    }

    private fun processDetails(details: List<ContactDetails>?) {
        val name: String = reduceName(details)
        val attributes: List<ContactAttribute> =
            details?.filter {
                it.dataType != ContactDetails.DetailsType.Name
            }?.map {
                when(it){
                    is ContactDetails.Phone -> ContactAttribute(
                        id = it.id,
                        valueType = ValueType.Phone,
                        labelRes = R.string.phone_number_caption,
                        value = it.phoneNumber,
                        accountType = it.accountType
                    )
                    is ContactDetails.Email -> ContactAttribute(
                        id = it.id,
                        valueType = ValueType.Email,
                        labelRes = R.string.email_caption,
                        value = it.email,
                        accountType = it.accountType
                    )
                    is ContactDetails.Website -> ContactAttribute(
                        id = it.id,
                        valueType = ValueType.Website,
                        labelRes = R.string.phone_number_caption,
                        value = it.url,
                        accountType = it.accountType
                    )
                    else -> throw IllegalArgumentException("Invalid type")
                }
            } ?: Collections.emptyList()

        _uiState.update {
            it.copy(
                loading = false,
                error = null,
                name = name,
                attributes = attributes,
                initial = name.firstOrNull()?.toString().orEmpty()
            )
        }
    }

    private fun reduceName(details: List<ContactDetails>?): String {
        val nameItem = details?.find { it.dataType == ContactDetails.DetailsType.Name }
        if(nameItem != null){
            return nameItem.displayName.orEmpty()
        }
        return details?.find { !it.displayName.isNullOrEmpty() } ?.displayName.orEmpty()
    }
}