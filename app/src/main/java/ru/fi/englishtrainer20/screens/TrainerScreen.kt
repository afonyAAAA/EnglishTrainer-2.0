package ru.fi.englishtrainer20.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import ru.fi.englishtrainer20.elementsInterface.CustomButton
import ru.fi.englishtrainer20.events.TrainerUIEvents
import ru.fi.englishtrainer20.repository.trainer.TrainerResults
import ru.fi.englishtrainer20.viewModels.TrainerViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TrainerScreen(navHostController: NavHostController){

    val trainerViewModel : TrainerViewModel = koinViewModel()

    val context = LocalContext.current

    ListenerResults(trainerViewModel = trainerViewModel, context = context)

    val stateAnimationTrainer = trainerViewModel.animationTrainerState
    val stateTrainer = trainerViewModel.trainerState
    val stateElementsTrainer = trainerViewModel.elementsTrainerState

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = stateElementsTrainer.snackBarHostState)
        }
    ) {
        AnimationStartTrainer(visible = stateAnimationTrainer.startTrainer) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                AnimationTargetWord(visible = stateAnimationTrainer.shiftTargetWord) {
                    PresentCardWord(stateTrainer.targetWord.word)
                }
                ListWords(stateTrainer.otherWords, trainerViewModel)
            }
        }
    }
}

@Composable
fun ListenerResults(trainerViewModel: TrainerViewModel, context : Context){
    LaunchedEffect(trainerViewModel, context){
        trainerViewModel.trainerResults.collect{ result ->
            when(result){
                is TrainerResults.CorrectedWord -> {
                    trainerViewModel.onEvent(TrainerUIEvents.NextWord)
                }
                is TrainerResults.NotCorrectedWord -> {
                    trainerViewModel.onEvent(TrainerUIEvents.NextWord)
                }
                is TrainerResults.UnknownError -> {

                }
                is TrainerResults.WordsIsLoaded -> {
                    trainerViewModel.onEvent(TrainerUIEvents.TrainerIsReady)
                }
            }
        }
    }
}

@Composable
fun AnimationStartTrainer(
    visible : MutableTransitionState<Boolean>,
    content : @Composable () -> Unit
){
    AnimatedVisibility(
        visibleState = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        content()
    }
}

@Composable
fun AnimationTargetWord(
    visible : MutableTransitionState<Boolean>,
    content : @Composable () -> Unit
){
    AnimatedVisibility(
        visibleState = visible,
        enter = slideInHorizontally(animationSpec = TweenSpec(300, 200)) + expandHorizontally(expandFrom = Alignment.End) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = {it -> it}) + shrinkHorizontally() + fadeOut()
    ) {
        content()
    }
}
@Composable
fun AnimationListWords(
    visible : MutableTransitionState<Boolean>,
    content : @Composable () -> Unit
){
    AnimatedVisibility(
        visibleState = visible,
        enter = slideInVertically(
            initialOffsetY = {it / 2},
            animationSpec = TweenSpec(500, 500))
                + expandVertically(initialHeight = {it / 2}),
        exit = slideOutVertically(targetOffsetY = {it / 2}) + fadeOut()
    ) {
        content()
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
fun InfoWordsLayout(){
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .padding(8.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Row{
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(color = Color.Green.copy(alpha = 0.5f))
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ){
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "")
                        Text(text = "Слово", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Слово", fontSize = 17.sp)
                        Text(text = "Слово", fontSize = 17.sp)
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(color = Color.Red.copy(alpha = 0.5f))
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ){
                        Icon(imageVector = Icons.Outlined.Close, contentDescription = "")
                        Text(text = "Слово", fontSize = 20.sp, fontWeight = FontWeight.Bold)
                        Text(text = "Слово", fontSize = 17.sp)
                        Text(text = "Слово", fontSize = 17.sp)
                    }
                }
            }
        }
        CustomButton(onClick = { /*TODO*/ }) {
            Text(text = "ОК")
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemCardWord(
    word : String,
    viewModel: TrainerViewModel
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(70.dp, 140.dp)
            .padding(10.dp)
            .clickable {
                viewModel.onEvent(TrainerUIEvents.UserChooseWord(word))
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ){
            Text(
                text = word
            )
        }
    }
}

@Composable
fun ListWords(words : List<String>, viewModel: TrainerViewModel){


    LazyColumn{
        items(items = words){ word ->
            AnimationListWords(visible = viewModel.animationTrainerState.shiftListWords) {
                ListItemCardWord(word = word, viewModel)
            }
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
        //ListWords(arrayListOf("ffff", "ffff", "ffff"))
    }
}


