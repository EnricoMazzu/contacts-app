package com.mzzlab.sample.contactsapp.ui.screen.details

import com.mzzlab.sample.contactsapp.ui.widget.UiError
import java.util.*

data class DetailsUiState(
    val loading: Boolean = false,
    val error: UiError? = null,
    val initial: String = "",
    val name:String = "",
    val values: List<Item> = Collections.emptyList()
){
    data class Item(
        val key: Int,
        val value: String? = null
    )
}