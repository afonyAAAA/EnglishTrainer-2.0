package ru.fi.englishtrainer20.events

import ru.fi.englishtrainer20.models.EnglishWord
import ru.fi.englishtrainer20.repository.trainer.TrainerResults

sealed class TrainerUIEvents{
     data class UserChooseWord(val word : String) : TrainerUIEvents()
     data class GetEnglishWord(val quantityWords : Int = 10) : TrainerUIEvents()
     object NextWord : TrainerUIEvents()
     object TrainerIsReady : TrainerUIEvents()
     object ShowNegativeSnackBar: TrainerUIEvents()
     object ShowPositiveSnackBar: TrainerUIEvents()
     object InfoButtonIsClicked : TrainerUIEvents()
     object DismissInfoWords : TrainerUIEvents()
     object EndAnimationTransitionPresentWord : TrainerUIEvents()
}
