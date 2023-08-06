package ru.fi.englishtrainer20.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.koin.java.KoinJavaComponent.get

import ru.fi.englishtrainer20.R
import ru.fi.englishtrainer20.elementsInterface.CustomButton
import ru.fi.englishtrainer20.navigation.NavRoutes
import ru.fi.englishtrainer20.viewModels.MainViewModel

@Composable
fun MainScreen(navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ){

        //val mainVM : MainViewModel = get(clazz = MainViewModel::class.java)

        CardTrainer(navHostController)
        CardArticles()
        CardStatistics()
    }
}

@Composable
fun CardTrainer(navHostController: NavHostController){
    ElevatedCard(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp),
        elevation = CardDefaults.elevatedCardElevation
            (defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ){
            Text(
                text = stringResource(R.string.Trainer),
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.ExtraBold,
                fontSize = 18.sp
            )

            Text(text = "Вы можете больше!")

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                CustomButton(
                    onClick = {
                        navHostController.navigate(NavRoutes.Trainer.route)
                    }
                ) {
                    Text(text = stringResource(R.string.StartTrainer), fontSize = 20.sp)
                }
            }
        }
    }
}

@Composable
fun CardArticles(){
    ElevatedCard(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation
            (defaultElevation = 10.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp),
            modifier = Modifier.padding(8.dp)
        ){
            Image(
                painter = painterResource(id = R.drawable.ic_launcher_background),
                contentDescription = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(125.dp, 200.dp)
                    .clip(RoundedCornerShape(15.dp)),
                alignment = Alignment.TopCenter,
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier.padding(start = 5.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Выучить слова не сложно",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.ExtraBold)
                Text(
                    text = "Текст....",
                    fontSize = 14.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

@Composable
fun CardStatistics(){
    ElevatedCard(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation
            (defaultElevation = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Статистика",
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold
            )

            LazyRow {
                items(14) {
                    Box(
                        modifier = Modifier
                            .heightIn(150.dp, 250.dp)
                            .width(20.dp)
                            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                    ) {
                        Canvas(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            drawLine(
                                start = Offset(x = size.width / 2, y = 0f),
                                end = Offset(x = size.width / 2, y = size.height),
                                color = Color.Green.copy(0.5f),
                                strokeWidth = 50F
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
            }

            Text(
                text = "Текст....",
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewCards(){
    val nav = rememberNavController()

    Column(verticalArrangement = Arrangement.SpaceEvenly, modifier = Modifier.fillMaxSize()){
        CardTrainer(nav)
        CardArticles()
        CardStatistics()
    }
}

