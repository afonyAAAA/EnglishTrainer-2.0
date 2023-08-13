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
import ru.fi.englishtrainer20.repository.trainer.TrainerRepository
import ru.fi.englishtrainer20.repository.trainer.TrainerResults
import ru.fi.englishtrainer20.stateClasses.trainer.AnimationTrainerState
import ru.fi.englishtrainer20.stateClasses.trainer.TrainerState
import ru.fi.englishtrainer20.stateClasses.trainer.UIElementsTrainerState

class TrainerViewModel(private val repository: TrainerRepository) : ViewModel() {

    var trainerState by mutableStateOf(TrainerState())
    var animationTrainerState by mutableStateOf(AnimationTrainerState())
    var elementsTrainerState by mutableStateOf(UIElementsTrainerState())

    private val trainerChannel = Channel<TrainerResults<Unit>>()
    val trainerResults = trainerChannel.receiveAsFlow()

    init {
        getEnglishWords(10)
    }

    fun onEvent(e : TrainerUIEvents){
        when(e){
            is TrainerUIEvents.UserChooseWord -> {
                checkCorrectOfWords(e.word)
            }
            is TrainerUIEvents.GetEnglishWord -> {
                getEnglishWords(e.quantityWords)
            }
            TrainerUIEvents.NextWord -> {
                getNextWord()
            }
            TrainerUIEvents.TrainerIsReady -> {
                startTrainer()
            }
        }
    }

    private fun getNextWord(){


        viewModelScope.launch {
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
        }

        trainerState = trainerState.copy(counterWord = trainerState.counterWord + 1)

        val nextWord = trainerState.listWords[trainerState.counterWord].word
        val nextCorrectWords = trainerState.listWords[trainerState.counterWord].russianWords

        trainerState = trainerState.copy(targetWord = EnglishWord(nextWord, nextCorrectWords))

        getNextOtherWords()
    }

    private fun getNextOtherWords(){

        val listWords : MutableList<String> = mutableListOf()

        repeat(2) {
            var otherWord : String? = null

            do {
                otherWord = trainerState.listWords.random().russianWords.firstOrNull {
                    !listWords.contains(it) && !trainerState.targetWord.russianWords.contains(it)
                }
            }while (otherWord == null)

            listWords.add(otherWord)
        }

        val correctWord = trainerState.targetWord.russianWords.random()
        listWords.add(correctWord)

        trainerState = trainerState.copy(
            otherWords = listWords
        )

    }

    private fun checkCorrectOfWords(chooseWord : String) {
        viewModelScope.launch {
            if(trainerState.targetWord.russianWords.any { it == chooseWord}){
                trainerChannel.send(TrainerResults.CorrectedWord())
            }else{
                trainerChannel.send(TrainerResults.NotCorrectedWord())
            }
        }
    }

    private fun startTrainer(){
        animationTrainerState = animationTrainerState.copy(
             startTrainer = MutableTransitionState(false).apply { this.targetState = true }
        )

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

//    fun countResult(){
//        val result = ResultTrainer(quantityWords, quantityCorrect)
//    }

}