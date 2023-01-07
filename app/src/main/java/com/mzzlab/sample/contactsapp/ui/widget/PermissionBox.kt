package com.mzzlab.sample.contactsapp.ui.widget

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.mzzlab.sample.contactsapp.R
import com.mzzlab.sample.contactsapp.common.isPermanentlyDenied

@Composable
fun PermissionBox(
    modifier: Modifier = Modifier,
    @StringRes rationaleMessage: Int,
    @StringRes permanentlyDeniedMessage: Int,
    isPermanentlyDenied: Boolean = false,
    onPermissionRequest: () -> Unit = {}
) {
    Card(
        modifier = modifier,
        elevation = 10.dp
    ) {
        Column(
            modifier = modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            @StringRes val textToShowRes = if(isPermanentlyDenied){
                permanentlyDeniedMessage
            }else{
                rationaleMessage
            }
            Text(stringResource(id = textToShowRes))
            Button(
                modifier = Modifier.padding(PaddingValues(top = 10.dp)),
                onClick = { onPermissionRequest() }
            ) {
                Text(stringResource(id = R.string.button_request_permission))
            }
        }
    }
}

@Preview(showBackground = false, name = "PermissionBoxPreview")
@Composable
private fun PermissionBoxPreview(){
    PermissionBox(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        rationaleMessage = R.string.contact_permission_rationale,
        permanentlyDeniedMessage = R.string.contact_permission_perm_denied
    )
}