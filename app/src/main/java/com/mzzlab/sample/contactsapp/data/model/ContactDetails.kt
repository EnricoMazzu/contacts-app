package com.mzzlab.sample.contactsapp.data.model

sealed interface ContactDetails {
    val id: String
    val contactId: String
    val accountType: String?
    val dataType: DetailsType
    val displayName: String?

    enum class DetailsType {
        Name,
        Phone,
        Email,
        WebSite
    }

    data class Name(
        override val id: String,
        override val contactId: String,
        override val accountType: String?,
        override val displayName: String? = null,
        override val dataType: DetailsType = DetailsType.Name
    ): ContactDetails

    data class Phone(
        override val id: String,
        override val contactId: String,
        override val accountType: String?,
        override val displayName: String? = null,
        override val dataType: DetailsType = DetailsType.Phone,
        val phoneType: Int = -1,
        val phoneNumber: String? = null,
        val phoneLabel: String? = null,
    ): ContactDetails

    data class Email(
        override val id: String,
        override val contactId: String,
        override val accountType: String?,
        override val displayName: String? = null,
        override val dataType: DetailsType = DetailsType.Email,
        val email: String? = null
    ): ContactDetails

    data class Website(
        override val id: String,
        override val contactId: String,
        override val accountType: String?,
        override val displayName: String? = null,
        override val dataType: DetailsType = DetailsType.WebSite,
        val url: String? = null
    ): ContactDetails
}
