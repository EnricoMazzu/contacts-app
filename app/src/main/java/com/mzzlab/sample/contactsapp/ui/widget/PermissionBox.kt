package com.mzzlab.sample.contactsapp.ui.widget

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.mzzlab.sample.contactsapp.R


@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun PermissionBox(
    modifier: Modifier = Modifier,
    permissionState: PermissionState,
    rationaleMessage: Int,
    permissionDesc: Int
) {
    val textToShow = if (permissionState.status.shouldShowRationale) {
        rationaleMessage
    } else {
        permissionDesc
    }
    Card(
        modifier = modifier,
        elevation = 10.dp
    ) {
        Column(
            modifier = modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(stringResource(id = textToShow))
            Button(
                modifier = Modifier.padding(PaddingValues(top = 10.dp)),
                onClick = { permissionState.launchPermissionRequest() }
            ) {
                Text(stringResource(id = R.string.button_request_permission))
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Preview(showBackground = false, name = "PermissionBoxPreview")
@Composable
private fun PermissionBoxPreview(){
    PermissionBox(
        modifier = Modifier,
        permissionState = DeniedMockPermissionState(true),
        rationaleMessage = R.string.contact_permission_rationale,
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
