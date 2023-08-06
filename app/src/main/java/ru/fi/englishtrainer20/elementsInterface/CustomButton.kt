package ru.fi.englishtrainer20.elementsInterface

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun CustomButton(
    onClick : () -> Unit,
    enabled : Boolean = true,
    modifier: Modifier = Modifier
        .heightIn(50.dp, 75.dp)
        .fillMaxWidth(),
    content: @Composable RowScope.() -> Unit
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