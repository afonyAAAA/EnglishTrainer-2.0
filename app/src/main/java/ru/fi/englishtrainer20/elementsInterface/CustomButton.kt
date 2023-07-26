package ru.fi.englishtrainer20.elementsInterface

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.Button

@Composable
fun CustomButton(
    onClick : () -> Unit,
    enabled : Boolean = true,
    modifier: Modifier = Modifier
        .heightIn(50.dp, 70.dp)
        .widthIn(150.dp, 200.dp),
    content: @Composable BoxScope.() -> Unit
){
    Button(
        onClick = {onClick()},
        enabled = enabled,
        shape = RoundedCornerShape(15.dp),
        modifier = modifier
    ) {
        content()
    }
}