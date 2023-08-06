package ru.fi.englishtrainer20.screens

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.koin.java.KoinJavaComponent.get
import ru.fi.englishtrainer20.models.EnglishWord
import ru.fi.englishtrainer20.viewModels.TrainerViewModel

@Composable
fun TrainerScreen(navHostController: NavHostController){

    val trainerViewModel : TrainerViewModel = get(TrainerViewModel::class.java)

    val trainerState = trainerViewModel.trainerState



    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        PresentCardWord(trainerState.targetWord.word)

        ListWords(trainerState.listWords.flatMap { it.russianWords })
    }
}

@Composable
fun PresentCardWord(word : String){
    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(30.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = word,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ListItemCardWord(word : String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp, 140.dp)
            .padding(10.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = word,
            )
        }
    }
}

@Composable
fun ListWords(words : List<String>){
    LazyColumn{
        items(items = words){ word ->
            ListItemCardWord(word = word)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewTrainer(){
    Column(
        Modifier.fillMaxSize()
    ) {
        PresentCardWord("fffff")
        ListWords(arrayListOf("ffff", "ffff", "ffff"))
    }
}
