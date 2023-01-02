package com.mzzlab.sample.contactsapp.ui.screen.details

import android.graphics.drawable.Icon
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.mzzlab.sample.contactsapp.R
import com.mzzlab.sample.contactsapp.ui.navigation.ContactDetailsRoute
import com.mzzlab.sample.contactsapp.ui.navigation.NavigationConstants
import com.mzzlab.sample.contactsapp.ui.theme.ContactsAppTheme
import com.mzzlab.sample.contactsapp.ui.widget.ContactInitial

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ContactDetailsScreen(viewModel: ContactDetailsViewModel) {
    LaunchedEffect(Unit){
        viewModel.reload()
    }
    val uiState: DetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    ContactDetailsContent(details = uiState)
}

@Composable
fun ContactDetailsContent(
    modifier: Modifier = Modifier,
    details: DetailsUiState
){
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(0.35f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ContactHeader(
                name = details.name.orEmpty(),
                initial = "C")
        }
        Row(
            Modifier
                .fillMaxWidth()
                .padding(15.dp)
                .weight(0.65f)) {
            /*Column(Modifier.fillMaxWidth()) {
                LabelValuePair(
                    label = stringResource(id = R.string.phone_number_caption),
                    value = details.phoneNumber.orEmpty()
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = Color.Transparent
                )
                LabelValuePair(
                    label = stringResource(id = R.string.email_caption),
                    value = details.email.orEmpty()
                )
                Divider(
                    Modifier
                        .fillMaxWidth()
                        .height(10.dp),
                    color = Color.Transparent
                )
                LabelValuePair(
                    label = stringResource(id = R.string.website_caption),
                    value = details.website.orEmpty()
                )

            }
            */

            LazyColumn(modifier = Modifier
                .fillMaxSize()){
                items(
                    items = details.values,
                    key = { i -> i.key}
                ){ item ->
                    LabelValuePair(
                        label = stringResource(id = item.key),
                        value = item.value.orEmpty()
                    )
                    Divider(
                        Modifier
                            .fillMaxWidth()
                            .height(10.dp),
                        color = Color.Transparent
                    )
                }
            }
        }

    }

}

@Composable
fun ContactHeader(
    modifier: Modifier = Modifier,
    name: String,
    initial: String,
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactInitial(
            color = Color.Red,
            initial = initial,
            circleSize = 100.dp,
            textStyle = MaterialTheme.typography.h2
        )
        Divider(
            Modifier
                .fillMaxWidth()
                .height(30.dp),
            color = Color.Transparent
        )
        Text(
            text = name,
            style = MaterialTheme.typography.h4,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun LabelValuePair(
    modifier: Modifier = Modifier,
    label: String,
    value: String,
){
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = label,
            style = MaterialTheme.typography.h5
        )
        Text(text = value)
    }
}


@Composable
@Preview(showBackground = true)
fun ContactDetailsContentPreview(){
    ContactsAppTheme {
        Surface(Modifier.fillMaxSize()) {
            ContactDetailsContent(
                details = DetailsUiState(
                    loading = false,
                    error = null,
                    name = "Cris field",
                )
            )
        }

    }
}


fun NavGraphBuilder.addContactDetailsScreen(
    navController: NavHostController
){
    composable(
        ContactDetailsRoute.route,
        arguments = listOf(
            navArgument(ContactDetailsRoute.CONTACT_ID_PARAMS){
                nullable = false
                type = NavType.StringType
            }
        )
    ){
        val contactId = it.arguments?.getString(ContactDetailsRoute.CONTACT_ID_PARAMS)
            ?: NavigationConstants.NoId
        val viewModel: ContactDetailsViewModel = hiltViewModel()
        viewModel.setContactId(contactId)
        ContactDetailsScreen(viewModel = viewModel)
    }
}