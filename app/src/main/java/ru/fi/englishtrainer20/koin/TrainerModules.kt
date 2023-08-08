package ru.fi.englishtrainer20.koin

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import ru.fi.englishtrainer20.repository.main.MainRepository
import ru.fi.englishtrainer20.repository.main.MainRepositoryImpl
import ru.fi.englishtrainer20.repository.trainer.TrainerRepositoryImpl
import ru.fi.englishtrainer20.viewModels.MainViewModel
import ru.fi.englishtrainer20.viewModels.TrainerViewModel

class TrainerModules() {

    val appModule = module {

        fun provideFirebase(): FirebaseFirestore {
            return FirebaseFirestore.getInstance()
        }

        single{
            provideFirebase()
        }

        val mainModule = module {
            single<MainRepository> { MainRepositoryImpl() }
            viewModelOf(::MainViewModel)
        }

        val trainerModule = module{
            singleOf(::TrainerRepositoryImpl)
            viewModelOf(::TrainerViewModel)
        }

        single {
            mainModule
        }

        single{
            trainerModule
        }
    }
}

