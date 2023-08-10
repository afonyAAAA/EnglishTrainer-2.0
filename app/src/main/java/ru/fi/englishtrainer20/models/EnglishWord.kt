package ru.fi.englishtrainer20.models

import kotlinx.coroutines.flow.MutableStateFlow

data class EnglishWord(
    val word : String = "",
    val russianWords : List<String> = arrayListOf()
)
