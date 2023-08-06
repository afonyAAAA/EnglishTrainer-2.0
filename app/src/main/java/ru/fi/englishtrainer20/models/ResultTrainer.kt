package ru.fi.englishtrainer20.models

data class ResultTrainer(
    val quantityWords : Int,
    val quantityCorrect : Int,
){
    val percentCorrect : Double = (quantityCorrect / quantityWords).toDouble() * 100.0
}
