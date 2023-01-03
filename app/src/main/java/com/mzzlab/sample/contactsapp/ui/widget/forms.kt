package com.mzzlab.sample.contactsapp.ui.widget

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun Line(height: Dp = 1.dp) {
    Divider(
        Modifier
            .fillMaxWidth()
            .height(height)
    )
}

