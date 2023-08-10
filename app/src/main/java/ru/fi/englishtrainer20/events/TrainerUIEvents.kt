package ru.fi.englishtrainer20.events

sealed class TrainerUIEvents{
     data class UserChooseWord(val word : String) : TrainerUIEvents()
     object NextWord : TrainerUIEvents()
     object GetEnglishWord : TrainerUIEvents()
     object TrainerIsReady : TrainerUIEvents()
}
