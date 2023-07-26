package ru.fi.englishtrainer20.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import ru.fi.englishtrainer20.R
import ru.fi.englishtrainer20.elementsInterface.CustomButton

@Composable
fun MainScreen(navHostController: NavHostController){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ){
        CardTrainer()
    }
}

@Composable
fun CardTrainer(){
    Card(
        shape = RoundedCornerShape(15.dp),
        modifier = Modifier.fillMaxWidth()
    ){
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
                    onClick = {},
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text(text = stringResource(R.string.StartTrainer))
                }
            }

        }
    }
}
@Preview(showBackground = true)
@Composable
fun PreviewCards(){
    CardTrainer()
}

