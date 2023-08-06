package ru.fi.englishtrainer20

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.context.startKoin
import ru.fi.englishtrainer20.koin.TrainerModules

class TrainerApp : Application() {
    override fun onCreate() {
        super.onCreate()

        val db = Firebase.firestore

        startKoin {
            modules(TrainerModules(db).listModules)
        }
    }
}