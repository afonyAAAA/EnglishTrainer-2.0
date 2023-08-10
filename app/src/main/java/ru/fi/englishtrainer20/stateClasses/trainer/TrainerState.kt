package ru.fi.englishtrainer20.stateClasses.trainer

import androidx.compose.material3.SnackbarHostState
import kotlinx.coroutines.flow.MutableStateFlow
import ru.fi.englishtrainer20.models.EnglishWord

data class TrainerState(
    val isLoading : Boolean = false,
    var _listWords : MutableStateFlow<List<EnglishWord>> = MutableStateFlow(emptyList()),
    var _targetWord : MutableStateFlow<EnglishWord> = MutableStateFlow(EnglishWord()),
    val quantityWords : Int = 0,
    val quantityCorrect : Int = 0,
    val counterWord : Int = 0,
)
