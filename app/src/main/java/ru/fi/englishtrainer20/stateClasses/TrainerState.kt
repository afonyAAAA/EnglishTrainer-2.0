package ru.fi.englishtrainer20.stateClasses

import kotlinx.coroutines.flow.MutableStateFlow
import ru.fi.englishtrainer20.models.EnglishWord

data class TrainerState(
    val isLoading : Boolean = false,
    val _listWords : MutableStateFlow<List<EnglishWord>> = MutableStateFlow(emptyList()),
    val _targetWord : MutableStateFlow<EnglishWord> = MutableStateFlow(EnglishWord()),
    val chooseWord : String = "",
    val quantityWords : Int = 0,
    val quantityCorrect : Int = 0,
    val counterWord : Int = 0
)
