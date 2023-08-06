package ru.fi.englishtrainer20.viewModels

import androidx.compose.runtime.collectAsState
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
import ru.fi.englishtrainer20.stateClasses.TrainerState


class TrainerViewModel(private val repository: TrainerRepository) : ViewModel() {

    var trainerState = TrainerState()

    private val trainerChannel = Channel<TrainerResults<Unit>>()

    val trainerResults = trainerChannel.receiveAsFlow()

    init {
        viewModelScope.launch {
            trainerState = trainerState.copy(isLoading = true)

            getEnglishWords()

            trainerState = trainerState.copy(
                targetWord = getNextWord()
            )

            trainerState = trainerState.copy(isLoading = false)
        }
    }

    fun onEvent(e : TrainerUIEvents){
        when(e){
            is TrainerUIEvents.UserChooseWord -> {

            }
        }
    }

    private fun getNextWord() : EnglishWord{

        val nextWord = trainerState.listWords[trainerState.counterWord].word
        val nextCorrectWords = trainerState.listWords[trainerState.counterWord].russianWords

        return EnglishWord(nextWord, nextCorrectWords)
    }

    fun checkCorrectOfWords() {

    }

    private suspend fun getEnglishWords(){
        trainerState = trainerState.copy(listWords = repository.getEnglishWords())
    }

//    fun countResult(){
//        val result = ResultTrainer(quantityWords, quantityCorrect)
//    }

}