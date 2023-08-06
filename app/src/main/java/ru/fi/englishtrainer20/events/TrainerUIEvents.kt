package ru.fi.englishtrainer20.events

sealed class TrainerUIEvents{
     data class UserChooseWord(val word : String) : TrainerUIEvents()
}
