package com.mzzlab.sample.contactsapp.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.mzzlab.sample.contactsapp.R


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PermissionAwareContainer(
    modifier: Modifier = Modifier,
    permission: String,
    @StringRes rationaleMsg: Int,
    @StringRes permissionDesc: Int,
    content:@Composable () -> Unit
){
    val cameraPermissionState = rememberPermissionState(permission)
    if (cameraPermissionState.status.isGranted) {
        content()
    } else {
        PermissionBox(
            modifier = modifier,
            cameraPermissionState = cameraPermissionState,
            rationaleMsg = rationaleMsg,
            permissionDesc = permissionDesc)
    }
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun PermissionBox(
    modifier: Modifier = Modifier,
    cameraPermissionState: PermissionState,
    rationaleMsg: Int,
    permissionDesc: Int
) {
    val textToShow = if (cameraPermissionState.status.shouldShowRationale) {
        rationaleMsg
    } else {
        permissionDesc
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(stringResource(id = textToShow))
        Button(onClick = {
            cameraPermissionState.launchPermissionRequest()
        }) {
            Text("Request permission")
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = false, name = "PermissionBoxPreview")
@Composable
private fun PermissionBoxPreview(){
    PermissionBox(
        modifier = Modifier.width(300.dp),
        cameraPermissionState = DeniedMockPermissionState(true),
        rationaleMsg = R.string.contact_permission_rationale,
        permissionDesc = R.string.contact_permission_desc
    )

}


@OptIn(ExperimentalPermissionsApi::class)
class OkMockPermissionState: PermissionState {
    override val permission: String
        get() = "MyPermission"
    override val status: PermissionStatus
        get() = PermissionStatus.Granted

    override fun launchPermissionRequest() {
        //NOOP
    }
}

@OptIn(ExperimentalPermissionsApi::class)
class DeniedMockPermissionState(private val shouldShowRationale: Boolean): PermissionState {
    override val permission: String
        get() = "MyPermission"
    override val status: PermissionStatus
        get() = PermissionStatus.Denied(shouldShowRationale)

    override fun launchPermissionRequest() {
        //NOOP
    }
}
