package ru.fi.englishtrainer20.screens

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.TweenSpec
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.snap
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import org.koin.androidx.compose.koinViewModel
import ru.fi.englishtrainer20.elementsInterface.CustomButton
import ru.fi.englishtrainer20.elementsInterface.Fonts
import ru.fi.englishtrainer20.events.TrainerUIEvents
import ru.fi.englishtrainer20.models.EnglishWord
import ru.fi.englishtrainer20.repository.trainer.TrainerResults
import ru.fi.englishtrainer20.stateClasses.trainer.TrainerState
import ru.fi.englishtrainer20.stateClasses.trainer.UIElementsTrainerState
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

    DialogWindows(
        stateElementsTrainer = stateElementsTrainer,
        stateTrainer = stateTrainer,
        viewModel = trainerViewModel
    )

    Scaffold(
        snackbarHost = {
            SnackbarHost(
                hostState = stateElementsTrainer.snackBarHostState,
                snackbar = {data ->
                    Snackbar(
                        action = {
                            IconButton(onClick = {trainerViewModel.onEvent(
                                TrainerUIEvents.InfoButtonIsClicked)
                            }) {
                                Icon(
                                    imageVector = Icons.Outlined.Info,
                                    tint = Color.Black,
                                    contentDescription = ""
                                )
                            }
                        },
                        containerColor = Color.White,
                        modifier = Modifier
                            .padding(8.dp),
                        contentColor = Color.Black,
                        shape = RoundedCornerShape(8.dp)
                    ){ Text(text = data.visuals.message)}
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            AnimationTargetWord(visible = stateAnimationTrainer.shiftTargetWord) {
                PresentCardWord(
                    word = stateTrainer.targetWord.word,
                    backForwardAnimationState =stateAnimationTrainer.backForwardTrainer.first,
                    onEndAnimation = {
                        trainerViewModel.onEvent(TrainerUIEvents.EndAnimationTransitionPresentWord)
                    }
                )
            }
            ListWords(stateTrainer.otherWords, trainerViewModel)
        }
    }
}

@Composable
fun DialogWindows(
    stateElementsTrainer : UIElementsTrainerState,
    stateTrainer : TrainerState,
    viewModel: TrainerViewModel
){
    if (stateElementsTrainer.stateNegativeDialog){
        NegativeDialogWindowInfo(
            correctWord = stateTrainer.pastTargetWord,
            notCorrectWord = stateTrainer.chooseWord,
            viewModel = viewModel
        )
    }
    if(stateElementsTrainer.statePositiveDialog){
        PositiveDialogWindowInfo(
            correctWord = stateTrainer.pastTargetWord,
            viewModel
        )
    }
}

@Composable
fun ListenerResults(trainerViewModel: TrainerViewModel, context : Context){
    LaunchedEffect(trainerViewModel, context){
        trainerViewModel.trainerResults.collect{ result ->
            when(result){
                is TrainerResults.CorrectedWord -> {
                    trainerViewModel.onEvent(TrainerUIEvents.ShowPositiveSnackBar)
                    trainerViewModel.onEvent(TrainerUIEvents.NextWord)
                }
                is TrainerResults.NotCorrectedWord -> {
                    trainerViewModel.onEvent(TrainerUIEvents.ShowNegativeSnackBar)
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
fun NegativeDialogWindowInfo(
    correctWord: EnglishWord,
    notCorrectWord: EnglishWord,
    viewModel: TrainerViewModel
) {
    Dialog(onDismissRequest = {viewModel.onEvent(TrainerUIEvents.DismissInfoWords)}) {
        InfoWordsLayout(
            correctWord = correctWord,
            notCorrectWord = notCorrectWord,
            onClickButton = {viewModel.onEvent(TrainerUIEvents.DismissInfoWords)}
        )
    }
}

@Composable
fun PositiveDialogWindowInfo(
    correctWord: EnglishWord,
    viewModel: TrainerViewModel
) {
    Dialog(onDismissRequest = {viewModel.onEvent(TrainerUIEvents.DismissInfoWords)}) {
        InfoWordsLayout(
            correctWord = correctWord,
            onClickButton = { viewModel.onEvent(TrainerUIEvents.DismissInfoWords) }
        )
    }
}

@Composable
fun AnimationTargetWord(
    visible : MutableTransitionState<Boolean>,
    content : @Composable () -> Unit
){
    AnimatedVisibility(
        visibleState = visible,
        enter = slideInHorizontally(animationSpec = TweenSpec(300, 200))
                + expandHorizontally(expandFrom = Alignment.End)
                + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = {it -> it})
                + shrinkHorizontally()
                + fadeOut()
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
fun PresentCardWord(
    word : String,
    backForwardAnimationState : Boolean,
    onEndAnimation : () -> Unit
){

    val translationX : Float by animateFloatAsState(
        targetValue = if(backForwardAnimationState) 100f else 0f,
        animationSpec = repeatable(
            iterations = 3,
            animation = tween(100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "",
        finishedListener = {
            if(it == 0F) onEndAnimation()
        }
    )

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.3f)
            .padding(8.dp)
            .graphicsLayer {
                this.translationX = translationX
            },
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
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = Fonts.comicNeueFamily
            )
        }
    }
}

@Composable
fun InfoWordsLayout(
    correctWord : EnglishWord,
    notCorrectWord : EnglishWord,
    onClickButton : () -> Unit
){
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
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
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ){
                        item {
                            Icon(imageVector = Icons.Outlined.Check, contentDescription = "")
                            Text(
                                text = correctWord.word,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        items(items = correctWord.russianWords){ correctWord ->
                            Text(text = correctWord, fontSize = 17.sp)
                        }
                    }
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(color = Color.Red.copy(alpha = 0.5f))
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                    ){
                        item {
                            Icon(imageVector = Icons.Outlined.Close, contentDescription = "")
                            Text(
                                text = notCorrectWord.word,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        items(items = notCorrectWord.russianWords){ notCorrectWord ->
                            Text(text = notCorrectWord, fontSize = 17.sp)
                        }
                    }
                }
            }
        }
        CustomButton(onClick = { onClickButton() }) {
            Text(text = "ОК")
        }
    }
}
@Composable
fun InfoWordsLayout(
    correctWord : EnglishWord,
    onClickButton : () -> Unit
){
    Column {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.6f)
                .padding(8.dp),
            shape = RoundedCornerShape(15.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .background(color = Color.Green.copy(alpha = 0.5f))
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                ){
                    item {
                        Icon(imageVector = Icons.Outlined.Check, contentDescription = "")
                        Text(
                            text = correctWord.word,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    items(items = correctWord.russianWords){ correctWord ->
                        Text(text = correctWord, fontSize = 17.sp)
                    }
                }
            }
        }
        CustomButton(onClick = { onClickButton() }) {
            Text(text = "ОК")
        }
    }
}

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
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
                .clickable {
                    viewModel.onEvent(TrainerUIEvents.UserChooseWord(word))
                },
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
        PresentCardWord("fffff", false) {}
        //ListWords(arrayListOf("ffff", "ffff", "ffff"))
    }
}


