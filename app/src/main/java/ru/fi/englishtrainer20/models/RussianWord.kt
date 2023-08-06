package ru.fi.englishtrainer20.models

data class RussianWord(
    val word : String,
    val englishWords : ArrayList<String> = arrayListOf()
)
