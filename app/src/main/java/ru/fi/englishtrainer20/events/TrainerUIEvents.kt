package ru.fi.englishtrainer20.events

sealed class TrainerUIEvents{
     data class UserChooseWord(val word : String) : TrainerUIEvents()
     object NextWord : TrainerUIEvents()
     data class GetEnglishWord(val quantityWords : Int = 10) : TrainerUIEvents()
     object TrainerIsReady : TrainerUIEvents()
}
