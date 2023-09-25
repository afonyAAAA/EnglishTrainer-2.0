package ru.fi.englishtrainer20.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.togetherWith
import androidx.compose.animation.with
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import kotlinx.coroutines.delay
import ru.fi.englishtrainer20.elementsInterface.CustomButton


@Composable
fun EndTrainerScreen(
    navHostController: NavHostController,
    percentCorrect : Int
){
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(15.dp, Alignment.CenterVertically)
    ) {
        PercentTrainer(percentCorrect)

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

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun PercentTrainer(limitPercent : Int){

    var percent by remember{ mutableStateOf(0) }

    LaunchedEffect(limitPercent){
        while (limitPercent > percent){
            percent++
            when(percent){
                in 0..5 -> delay(150L)
                in 10..50 -> delay(100L)
                in 50..100 -> delay(50L)
            }
        }
    }

    AnimatedContent(
        targetState = percent,
        label = "",
        transitionSpec = {
            slideIntoContainer(
                towards = AnimatedContentTransitionScope.SlideDirection.Up,
                animationSpec = tween(durationMillis = 250)
            ) togetherWith
                slideOutOfContainer(
                    towards = AnimatedContentTransitionScope.SlideDirection.Down,
                    animationSpec = tween(durationMillis = 250)
                )
        },
        contentAlignment = Alignment.Center
    ) { percent ->
        Text(
            text = "$percent%",
            fontSize = 60.sp,
        )
    }
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
    //EndTrainerScreen(navHostController = rememberNavController())
}