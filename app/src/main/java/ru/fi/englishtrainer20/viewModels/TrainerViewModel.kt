package ru.fi.englishtrainer20.viewModels

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

    val listWords = trainerState._listWords.asStateFlow()
    val targetWord = trainerState._targetWord.asStateFlow()

    private val trainerChannel = Channel<TrainerResults<Unit>>()
    val trainerResults = trainerChannel.receiveAsFlow()

    fun onEvent(e : TrainerUIEvents){
        when(e){
            is TrainerUIEvents.UserChooseWord -> {
                checkCorrectOfWords(e.word)
            }
            TrainerUIEvents.NextWord -> {
                getNextWord()
            }
            TrainerUIEvents.GetEnglishWord -> {
                getEnglishWords()
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

        trainerState._listWords.value.shuffled()

        getNextWord()

        animationTrainerState = animationTrainerState.copy(
            fade = true
        )

    }

    private fun getEnglishWords(){
        viewModelScope.launch {

            val words = repository.getEnglishWords()

            trainerState._listWords.value = words

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