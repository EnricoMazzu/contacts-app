package com.mzzlab.sample.contactsapp.ui.screen.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.text.font.FontStyle
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
import com.mzzlab.sample.contactsapp.ui.widget.Line
import com.mzzlab.sample.contactsapp.ui.widget.rememberContactColor

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun ContactDetailsScreen(viewModel: ContactDetailsViewModel) {
    LaunchedEffect(Unit) {
        viewModel.reload()
    }
    val uiState: DetailsUiState by viewModel.uiState.collectAsStateWithLifecycle()
    ContactDetailsContent(details = uiState, contactId = viewModel.contactId)
}

@Composable
fun ContactDetailsContent(
    modifier: Modifier = Modifier,
    contactId: String,
    details: DetailsUiState
) {
    Column(modifier = modifier.fillMaxSize()) {
        Row(
            Modifier
                .fillMaxWidth()
                .weight(0.35f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val color = rememberContactColor(contactId = contactId)
            ContactHeader(
                name = details.name,
                initial = details.initial,
                initialColor = color
            )
        }
        Row(
            Modifier
                .fillMaxWidth()
                .weight(0.65f)
        ) {
            ContactDetailsList(
                modifier = Modifier.fillMaxSize(),
                details = details
            )
        }

    }
}

@Composable
private fun ContactDetailsList(
    modifier: Modifier = Modifier,
    details: DetailsUiState
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = details.attributes,
            key = { i -> i.id }
        ) { item ->
            Line()
            LabelValuePair(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp),
                label = stringResource(id = item.labelRes),
                value = item.value.orEmpty(),
                accountType = item.accountType
            )
            Line()
            Spacer(
                Modifier
                    .fillMaxWidth()
                    .height(10.dp),
            )
        }
    }
}

@Composable
fun ContactHeader(
    modifier: Modifier = Modifier,
    name: String,
    initial: String,
    initialColor: Color = Color.Red
) {
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ContactInitial(
            color = initialColor,
            initial = initial,
            circleSize = 100.dp,
            textStyle = MaterialTheme.typography.h2,
        )
        Spacer(
            Modifier
                .fillMaxWidth()
                .height(30.dp))
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
    accountType: String ? = null
) {
    Column(modifier = modifier
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.h5
        )
        Text(text = value)
        Text(
            modifier = Modifier
                .padding(top = 5.dp),
            text = accountType.orEmpty(),
            style = MaterialTheme.typography.overline
                .copy(fontStyle = FontStyle.Italic))
    }
}

@Composable
@Preview(showBackground = true)
fun ContactDetailsContentPreview() {
    ContactsAppTheme {
        Surface(Modifier.fillMaxSize()) {
            ContactDetailsContent(
                contactId = "23",
                details = DetailsUiState(
                    loading = false,
                    error = null,
                    initial = "C",
                    name = "Cris field",
                    attributes = listOf(
                        ContactAttribute(
                            id = "1",
                            valueType = ValueType.Phone,
                            labelRes =R.string.phone_number_caption,
                            value = "+39 5645324",
                            accountType = "com.google"
                        ),
                        ContactAttribute(
                            id = "2",
                            valueType = ValueType.Email,
                            labelRes =R.string.email_caption,
                            value = "+39 5645324",
                            accountType = "com.google"
                        ),
                        ContactAttribute(
                            id = "3",
                            valueType = ValueType.Website,
                            labelRes =R.string.website_caption,
                            value = "https://www.google.it",
                            accountType = "com.google"
                        ),
                    )
                )
            )
        }

    }
}


fun NavGraphBuilder.addContactDetailsScreen() {
    composable(
        ContactDetailsRoute.route,
        arguments = listOf(
            navArgument(ContactDetailsRoute.CONTACT_ID_PARAMS) {
                nullable = false
                type = NavType.StringType
            }
        )
    ) {
        val contactId = it.arguments?.getString(ContactDetailsRoute.CONTACT_ID_PARAMS)
            ?: NavigationConstants.NoId
        val viewModel: ContactDetailsViewModel = hiltViewModel()
        viewModel.setTargetContactId(contactId)
        ContactDetailsScreen(viewModel = viewModel)
    }
}