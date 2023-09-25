package ru.fi.englishtrainer20.repository.trainer

import android.util.Log
import com.google.firebase.firestore.AggregateSource
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ru.fi.englishtrainer20.models.EnglishWord


class TrainerRepositoryImpl(private val db : FirebaseFirestore) : TrainerRepository {

    override suspend fun getEnglishWords(limit : Int) : List<EnglishWord> {

        val countQuery = db.collection("EnglishWords")
            .count()
            .get(AggregateSource.SERVER)
            .addOnFailureListener {
                Log.d("ERROR", it.stackTrace.toString())
            }.await().count

        val countOtherWords = when(limit){
            10 -> {
                5
            }
            20 -> {
                10
            }
            else -> {
                0
            }
        }

        val upperLimit = countQuery - limit + countOtherWords

        val querySnapshot = db.collection("EnglishWords")
            .orderBy("word")
            .startAt((0..upperLimit).random())
            .limit((limit + countOtherWords).toLong())
            .get()
            .addOnFailureListener {
                Log.d("ERROR", it.stackTrace.toString())
        }.await()

        return querySnapshot.toObjects(EnglishWord::class.java)
    }
}