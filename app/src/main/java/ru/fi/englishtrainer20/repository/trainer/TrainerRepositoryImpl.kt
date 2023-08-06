package ru.fi.englishtrainer20.repository.trainer

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import ru.fi.englishtrainer20.models.EnglishWord


class TrainerRepositoryImpl(private val db : FirebaseFirestore) : TrainerRepository {

    override suspend fun getEnglishWords() : List<EnglishWord> {

        val querySnapshot = db.collection("EnglishWords").get().await()

        return querySnapshot.toObjects(EnglishWord::class.java)
    }
}