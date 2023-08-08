package ru.fi.englishtrainer20.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import ru.fi.englishtrainer20.events.TrainerUIEvents
import ru.fi.englishtrainer20.models.EnglishWord
import ru.fi.englishtrainer20.repository.trainer.TrainerRepository
import ru.fi.englishtrainer20.repository.trainer.TrainerResults
import ru.fi.englishtrainer20.stateClasses.TrainerState

class TrainerViewModel(private val repository: TrainerRepository) : ViewModel() {

    private var trainerState = TrainerState()

    val listWords = trainerState._listWords.asStateFlow()
    val targetWord = trainerState._targetWord.asStateFlow()

    private val trainerChannel = Channel<TrainerResults<Unit>>()

    val trainerResults = trainerChannel.receiveAsFlow()

    init {

        trainerState = trainerState.copy(isLoading = true)

        getEnglishWords()

        getNextWord()

        trainerState = trainerState.copy(isLoading = false)

    }

    fun onEvent(e : TrainerUIEvents){
        when(e){
            is TrainerUIEvents.UserChooseWord -> {
                checkCorrectOfWords()
            }
            TrainerUIEvents.NextWord -> {
                getNextWord()
            }
        }
    }

    private fun getNextWord(){

        val nextWord = trainerState._listWords.value[trainerState.counterWord].word
        val nextCorrectWords = trainerState._listWords.value[trainerState.counterWord].russianWords

        trainerState = trainerState.copy(
            _targetWord = MutableStateFlow(EnglishWord(nextWord, nextCorrectWords))
        )
    }

    private fun checkCorrectOfWords() {
        viewModelScope.launch {
            if(targetWord.value.russianWords.any { it == trainerState.chooseWord }){
                trainerChannel.send(TrainerResults.CorrectedWord())
            }
        }
    }

    private fun getEnglishWords(){
        viewModelScope.launch {
            trainerState = trainerState.copy(
                _listWords = MutableStateFlow(repository.getEnglishWords())
            )
        }
    }

//    fun countResult(){
//        val result = ResultTrainer(quantityWords, quantityCorrect)
//    }

}