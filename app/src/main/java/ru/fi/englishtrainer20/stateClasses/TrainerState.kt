package ru.fi.englishtrainer20.stateClasses

import ru.fi.englishtrainer20.models.EnglishWord

data class TrainerState(
    val isLoading : Boolean = false,
    val listWords : List<EnglishWord> = listOf(),
    val targetWord : EnglishWord = EnglishWord(),
    val quantityWords : Int = 0,
    val quantityCorrect : Int = 0,
    val counterWord : Int = 0
)
