package ru.fi.englishtrainer20

import android.app.Application
import android.util.Log
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module
import ru.fi.englishtrainer20.koin.TrainerModules

class TrainerApp : Application() {
    override fun onCreate() {
        super.onCreate()

            val module = TrainerModules().appModule

            startKoin {
                modules(module)
            }
    }
}