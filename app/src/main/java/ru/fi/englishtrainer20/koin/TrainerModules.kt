package ru.fi.englishtrainer20.koin

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.scope.get
import org.koin.dsl.module
import org.koin.java.KoinJavaComponent.get
import ru.fi.englishtrainer20.repository.main.MainRepository
import ru.fi.englishtrainer20.repository.main.MainRepositoryImpl
import ru.fi.englishtrainer20.repository.trainer.TrainerRepository
import ru.fi.englishtrainer20.repository.trainer.TrainerRepositoryImpl
import ru.fi.englishtrainer20.viewModels.MainViewModel
import ru.fi.englishtrainer20.viewModels.TrainerViewModel

class TrainerModules(db : FirebaseFirestore) {

    private val mainModule = module {
        single<MainRepository> { MainRepositoryImpl() }
        factory { MainViewModel(get()) }
    }

    private val trainerModule = module{
        single<TrainerRepository> { TrainerRepositoryImpl(db) }
        factory { TrainerViewModel(get()) }
    }

    val listModules = listOf(mainModule, trainerModule)

}

