package com.mzzlab.sample.contactsapp.ui.widget

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.mzzlab.sample.contactsapp.ui.theme.contactColors

@Composable
fun ContactInitial(
    modifier: Modifier = Modifier,
    color: Color,
    initial: String,
    circleSize: Dp = 40.dp,
    textStyle: TextStyle = MaterialTheme.typography.subtitle1,
) {
    Box(modifier.size(circleSize), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawCircle(SolidColor(color))
        }
        Text(text = initial, style = textStyle, color = Color.White)
    }
}

@Composable
fun rememberContactColor(contactId: String) = remember(contactId){
    contactColors[contactId.hashCode() % contactColors.size]
}

