package com.mzzlab.sample.contactsapp.common

import com.google.accompanist.permissions.*

@OptIn(ExperimentalPermissionsApi::class)
fun PermissionStatus.isPermanentlyDenied(): Boolean{
    return !this.shouldShowRationale && !isGranted
}
