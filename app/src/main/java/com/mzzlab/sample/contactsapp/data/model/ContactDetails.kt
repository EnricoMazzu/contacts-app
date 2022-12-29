package com.mzzlab.sample.contactsapp.data.model

sealed class ContactDetails(
    val id: String,
    val contactId: String,
    val type: DetailsType,
    val displayName: String? = null
) {
    enum class DetailsType {
        Name,
        Phone,
        Email,
        WebSite,
        Other
    }



    class Name(
        id: String,
        contactId: String,
        displayName: String? = null,
    ):ContactDetails(id, contactId, DetailsType.Name, displayName){
        override fun toString(): String {
            return "Name() ${super.toString()}"
        }
    }

    class Phone(
        id: String,
        contactId: String,
        displayName: String? = null,
        val phoneType: Int = -1,
        val phoneNumber: String? = null,
        val phoneLabel: String? = null,
    ): ContactDetails(id, contactId, DetailsType.Phone, displayName) {
        override fun toString(): String {
            return "Phone(phoneType=$phoneType, phoneNumber=$phoneNumber, phoneLabel=$phoneLabel) ${super.toString()}"
        }
    }

    class Email(
        id: String,
        contactId: String,
        displayName: String? = null,
        val email: String? = null
    ): ContactDetails(id, contactId, DetailsType.Email, displayName){
        override fun toString(): String {
            return "Email(email=$email) ${super.toString()}"
        }
    }

    class Website(
        id: String,
        contactId: String,
        displayName: String? = null,
        val url: String? = null
    ): ContactDetails(id, contactId, DetailsType.WebSite, displayName){
        override fun toString(): String {
            return "Website(url=$url) ${super.toString()}"
        }
    }

    class Other(
        id: String,
        contactId: String,
        displayName: String? = null,
        val data1: String? = null,
        val data2: String? = null,
        val data3: String? = null
    ): ContactDetails(id, contactId, DetailsType.Other, displayName)

    override fun toString(): String {
        return "ContactDetails(id='$id', contactId='$contactId', type=$type, displayName=$displayName)"
    }
}





