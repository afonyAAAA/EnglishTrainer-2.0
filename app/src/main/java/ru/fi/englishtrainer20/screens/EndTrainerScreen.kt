package ru.fi.englishtrainer20.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ru.fi.englishtrainer20.elementsInterface.CustomButton


@Composable
fun EndTrainerScreen(navHostController: NavHostController){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically)
    ) {
        PercentTrainer()

        LoadingLineResult()

        MessageUserOfResult()

        CustomButton(
            onClick = { /*TODO*/ },
            modifier = Modifier
                .heightIn(50.dp, 75.dp)
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp)
        ) {
            Text(text = "ОК")
        }
    }
}

@Composable
fun PercentTrainer(){
    Text(
        text = "100%",
        fontSize = 60.sp
    )
}

@Composable
fun MessageUserOfResult(){
    Text(
        text = "Неплох",
        fontSize = 15.sp,
        color = Color.Gray
    )
}

@Composable
fun LoadingLineResult(){
    Canvas(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(8.dp)
            .clip(RoundedCornerShape(15.dp)),
        onDraw = {
            drawLine(
                brush = Brush.horizontalGradient(listOf(Color.Green, Color.Red)),
                start = Offset(x = size.width, y = 0f),
                end = Offset(x = 0f, y = 0f),
                strokeWidth = size.height * 2
            )
        }
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewEndTrainer(){
    EndTrainerScreen(navHostController = rememberNavController())
}