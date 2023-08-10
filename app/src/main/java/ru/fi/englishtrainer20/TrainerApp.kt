package ru.fi.englishtrainer20

import android.app.Application
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import ru.fi.englishtrainer20.koin.TrainerModules

class TrainerApp : Application() {
    override fun onCreate() {
        super.onCreate()

            val modules = TrainerModules().trainerModules

            startKoin {
                androidLogger()
                androidContext(this@TrainerApp)
                modules(modules)
            }
    }
}