package ru.fi.englishtrainer20.repository.trainer

import ru.fi.englishtrainer20.models.EnglishWord


interface TrainerRepository {
    suspend fun getEnglishWords(limit : Int) : List<EnglishWord>
}