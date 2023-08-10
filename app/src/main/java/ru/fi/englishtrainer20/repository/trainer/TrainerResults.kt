package ru.fi.englishtrainer20.repository.trainer

sealed class TrainerResults<T>(val data : T? = null){
    class NotCorrectedWord<T>(data: T? = null) : TrainerResults<T>(data)
    class CorrectedWord<T>(data: T? = null) : TrainerResults<T>(data)
    class WordsIsLoaded<T>(data: T? = null) : TrainerResults<T>(data)
    class UnknownError<T>(data: T? = null) : TrainerResults<T>(data)

}
