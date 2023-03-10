package com.mzzlab.sample.contactsapp.ui.screen.details

import com.mzzlab.sample.contactsapp.ui.widget.UiError
import java.util.*

data class DetailsUiState(
    val loading: Boolean = false,
    val error: UiError? = null,
    val initial: String = "#",
    val name:String = "",
    val attributes: List<ContactAttribute> = Collections.emptyList()
)

data class ContactAttribute(
    val id: String,
    val valueType: ValueType,
    val labelRes: Int,
    val value: String?,
    val accountType: String? = null,
)

enum class ValueType {
    Phone,
    Email,
    Website
}