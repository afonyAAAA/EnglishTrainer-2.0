package ru.fi.englishtrainer20.viewModels

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.fi.englishtrainer20.events.TrainerUIEvents
import ru.fi.englishtrainer20.models.EnglishWord
import ru.fi.englishtrainer20.models.ResultTrainer
import ru.fi.englishtrainer20.repository.trainer.TrainerRepository
import ru.fi.englishtrainer20.repository.trainer.TrainerResults
import ru.fi.englishtrainer20.stateClasses.trainer.AnimationTrainerState
import ru.fi.englishtrainer20.stateClasses.trainer.TrainerState
import ru.fi.englishtrainer20.stateClasses.trainer.UIElementsTrainerState
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class TrainerViewModel(private val repository: TrainerRepository) : ViewModel() {

    var trainerState by mutableStateOf(TrainerState())
    var animationTrainerState by mutableStateOf(AnimationTrainerState())
    var elementsTrainerState by mutableStateOf(UIElementsTrainerState())

    private val trainerChannel = Channel<TrainerResults<Unit>>()
    val trainerResults = trainerChannel.receiveAsFlow()

    init {
        getEnglishWords(trainerState.quantityWords)
    }

    fun onEvent(e : TrainerUIEvents){
        when(e){
            is TrainerUIEvents.UserChooseWord -> {

                trainerState = trainerState.copy(
                    counterWord = trainerState.counterWord + 1,
                    chooseWord = trainerState.listWords.find { it.russianWords.contains(e.word) } ?: EnglishWord()
                )

                checkCorrectOfWords()

            }
            is TrainerUIEvents.GetEnglishWord -> {
                getEnglishWords(e.quantityWords)
            }
            TrainerUIEvents.NextWord -> {
                if(trainerState.counterWord == trainerState.listWords.size){
                    countResult()
                }else{
                    getNextWord()
                }
            }
            TrainerUIEvents.TrainerIsReady -> {
                startTrainer()
            }
            TrainerUIEvents.ShowNegativeSnackBar -> {
                viewModelScope.launch {
                    trainerState = trainerState.copy(
                        lastResult = false
                    )
                    elementsTrainerState.snackBarHostState.showSnackbar(
                        "Неправильно"
                    )
                }
            }
            TrainerUIEvents.ShowPositiveSnackBar -> {
                viewModelScope.launch {
                    trainerState = trainerState.copy(
                        lastResult = true
                    )
                    elementsTrainerState.snackBarHostState.showSnackbar(
                        "Правильно"
                    )
                }
            }
            TrainerUIEvents.InfoButtonIsClicked -> {
                viewModelScope.launch {
                    elementsTrainerState = if(trainerState.lastResult){
                        elementsTrainerState.copy(
                            statePositiveDialog = true
                        )
                    }else {
                        elementsTrainerState.copy(
                            stateNegativeDialog = true
                        )
                    }
                }
            }
            TrainerUIEvents.DismissInfoWords -> {
                elementsTrainerState = elementsTrainerState.copy(
                    stateNegativeDialog = false,
                    statePositiveDialog = false
                )
            }

            TrainerUIEvents.EndAnimationTransitionPresentWord -> {
                animationTrainerState = animationTrainerState.copy(
                    backForwardTrainer = Pair(animationTrainerState.backForwardTrainer.first, true)
                )
            }
        }
    }
    private fun getNextWord(){
        viewModelScope.launch {

            trainerState = trainerState.copy(pastTargetWord = trainerState.targetWord)

            playAnimationWords()

            elementsTrainerState = elementsTrainerState.copy(
                listItemsIsClickable = true
            )

            val nextWord = trainerState.listWords[trainerState.counterWord].word
            val nextCorrectWords = trainerState.listWords[trainerState.counterWord].russianWords

            trainerState = trainerState.copy(
                targetWord = EnglishWord(nextWord, nextCorrectWords)
            )

            getNextOtherWords()
        }
    }

    private fun getNextOtherWords(){

        val listWords : MutableList<String> = mutableListOf()

        repeat(2) {
            var otherWord : String? = null

            do {
                otherWord = trainerState.listWords
                    .filter { it != trainerState.targetWord }
                    .random()
                    .russianWords
                    .firstOrNull {
                    !listWords.contains(it)
                }
            }while (otherWord == null)

            listWords.add(otherWord)
        }

        val correctWord = trainerState.targetWord.russianWords.random()
        listWords.add(correctWord)

        trainerState = trainerState.copy(
            otherWords = listWords.shuffled()
        )

    }

    private fun checkCorrectOfWords() {
        viewModelScope.launch {
            if(trainerState.targetWord.russianWords.any {trainerState.chooseWord.russianWords.contains(it)}){

                trainerChannel.send(TrainerResults.CorrectedWord())

                trainerState = trainerState.copy(quantityCorrect = trainerState.quantityCorrect + 1)

            }else{
                trainerChannel.send(TrainerResults.NotCorrectedWord())
            }
        }
    }

    private fun startTrainer(){
        getNextWord()
    }

    private fun getEnglishWords(quantityWords : Int){
        viewModelScope.launch {

            val words = repository.getEnglishWords(quantityWords)

            val shuffledWords = words.shuffled()

            trainerState = trainerState.copy(
                listWords = shuffledWords
            )

            if(trainerState.listWords.isNotEmpty()){
                trainerChannel.send(TrainerResults.WordsIsLoaded())
            }else{
                trainerChannel.send(TrainerResults.UnknownError())
            }

        }

    }

    private suspend fun playAnimationWords(){

        suspend fun waitEndAnimation(){
            while (!animationTrainerState.backForwardTrainer.second) {
                delay(10)
            }
        }

        suspendCoroutine{
            viewModelScope.launch {
                if(!trainerState.lastResult && trainerState.counterWord != 0){

                    elementsTrainerState = elementsTrainerState.copy(
                        listItemsIsClickable = false
                    )

                    repeat(3){
                        animationTrainerState = animationTrainerState.copy(
                            backForwardTrainer = Pair(true, false)
                        )

                        waitEndAnimation()

                        animationTrainerState = animationTrainerState.copy(
                            backForwardTrainer = Pair(false, false)
                        )

                        waitEndAnimation()

                    }
                    delay(500L)
                }

                if(trainerState.counterWord != 0){
                    animationTrainerState = animationTrainerState.copy(
                        shiftTargetWord = MutableTransitionState(true).apply { this.targetState = false },
                        shiftListWords = MutableTransitionState(true).apply { this.targetState = false }
                    )
                }

                delay(1500L)

                animationTrainerState = animationTrainerState.copy(
                    shiftTargetWord = MutableTransitionState(false).apply { this.targetState = true },
                    shiftListWords = MutableTransitionState(false).apply { this.targetState = true }
                )

                it.resume(Unit)
            }
        }
    }

    private fun countResult(){
        viewModelScope.launch {
            val result = ResultTrainer(trainerState.quantityWords, trainerState.quantityCorrect).percentCorrect
            trainerState = trainerState.copy(percentCorrect = result)
            trainerChannel.send(TrainerResults.TrainerIsEnd())
        }
    }

}