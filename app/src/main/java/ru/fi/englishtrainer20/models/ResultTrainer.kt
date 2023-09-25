package ru.fi.englishtrainer20.models

data class ResultTrainer(
    val quantityWords : Int,
    val quantityCorrect : Int,
){
    val percentCorrect : Int = (quantityCorrect / quantityWords) * 100
}
