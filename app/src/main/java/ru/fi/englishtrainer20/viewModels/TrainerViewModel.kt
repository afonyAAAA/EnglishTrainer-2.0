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

    val targetWord = trainerState._targetWord.asStateFlow()
    val otherWords = trainerState._otherWords.asStateFlow()

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

        val nextWord = trainerState._listWords.value[trainerState.counterWord].word
        val nextCorrectWords = trainerState._listWords.value[trainerState.counterWord].russianWords

        trainerState._targetWord.value = EnglishWord(nextWord, nextCorrectWords)

        getNextOtherWords()

        animationTrainerState = animationTrainerState.copy(
            shiftTargetWord = MutableTransitionState(),
            shiftListWords = true
        )

    }

    private fun getNextOtherWords(){

        val listWords : MutableList<String> = mutableListOf()

        repeat(2) {
            var otherWord : String? = null

            do {
                 otherWord = trainerState._listWords.value.random().russianWords.firstOrNull { !listWords.contains(it) }
            }while (otherWord == null)

            listWords.add(otherWord)
        }

        val correctWord = trainerState._targetWord.value.russianWords.random()
        listWords.add(correctWord)

        trainerState._otherWords.value = listWords

    }

    private fun checkCorrectOfWords(chooseWord : String) {
        viewModelScope.launch {
            if(targetWord.value.russianWords.any { it == chooseWord}){
                trainerChannel.send(TrainerResults.CorrectedWord())
            }else{
                trainerChannel.send(TrainerResults.NotCorrectedWord())
            }
        }
    }

    private fun startTrainer(){

        getNextWord()

        animationTrainerState = animationTrainerState.copy(
             startTrainer = true
        )

    }

    private fun getEnglishWords(quantityWords : Int){
        viewModelScope.launch {

            val words = repository.getEnglishWords(quantityWords)

            val shuffledWords = words.shuffled()

            trainerState._listWords.value = shuffledWords

            if(trainerState._listWords.value.isNotEmpty()){
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