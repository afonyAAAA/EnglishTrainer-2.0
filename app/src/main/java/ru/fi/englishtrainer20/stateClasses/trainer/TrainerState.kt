package ru.fi.englishtrainer20.stateClasses.trainer

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.flow.MutableStateFlow
import ru.fi.englishtrainer20.models.EnglishWord
import ru.fi.englishtrainer20.repository.trainer.TrainerResults

data class TrainerState(
    val listWords : List<EnglishWord> = mutableListOf(),
    val targetWord : EnglishWord = EnglishWord(),
    val chooseWord : EnglishWord = EnglishWord(),
    val pastTargetWord : EnglishWord = EnglishWord(),
    val otherWords : List<String> = listOf(),
    val quantityWords : Int = 0,
    val quantityCorrect : Int = 0,
    val counterWord : Int = 0,
    val lastResult : Boolean = false,
    val percentCorrect : Int = 0
)
