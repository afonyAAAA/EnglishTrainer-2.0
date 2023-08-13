package ru.fi.englishtrainer20.stateClasses.trainer

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.flow.MutableStateFlow
import ru.fi.englishtrainer20.models.EnglishWord

data class TrainerState(
    val isLoading : Boolean = false,
    val listWords : List<EnglishWord> = mutableListOf(),
    val targetWord : EnglishWord = EnglishWord(),
    val otherWords : List<String> = listOf(),
    val quantityWords : Int = 0,
    val quantityCorrect : Int = 0,
    val counterWord : Int = 0,
)
