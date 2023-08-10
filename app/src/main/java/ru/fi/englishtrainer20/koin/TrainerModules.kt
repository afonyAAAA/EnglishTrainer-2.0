package ru.fi.englishtrainer20.koin

import com.google.firebase.firestore.FirebaseFirestore
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module
import ru.fi.englishtrainer20.repository.main.MainRepository
import ru.fi.englishtrainer20.repository.main.MainRepositoryImpl
import ru.fi.englishtrainer20.repository.trainer.TrainerRepository
import ru.fi.englishtrainer20.repository.trainer.TrainerRepositoryImpl
import ru.fi.englishtrainer20.viewModels.MainViewModel
import ru.fi.englishtrainer20.viewModels.TrainerViewModel

class TrainerModules {
    private fun provideFirebase(): FirebaseFirestore = FirebaseFirestore.getInstance()

    private val mainModule = module {
        single<MainRepository> { MainRepositoryImpl() }
        viewModelOf(::MainViewModel)
    }

    private val trainerModule = module{
        single { provideFirebase() }
        factory<TrainerRepository>{TrainerRepositoryImpl(get())}
        viewModel{TrainerViewModel(get())}
    }

    val trainerModules = listOf(trainerModule, mainModule)

}

