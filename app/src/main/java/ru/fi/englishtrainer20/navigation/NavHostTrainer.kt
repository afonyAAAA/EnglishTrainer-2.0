package ru.fi.englishtrainer20.navigation

import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.koin.androidx.compose.koinViewModel
import ru.fi.englishtrainer20.screens.EndTrainerScreen
import ru.fi.englishtrainer20.screens.MainScreen
import ru.fi.englishtrainer20.screens.TrainerScreen
import ru.fi.englishtrainer20.viewModels.TrainerViewModel

sealed class NavRoutes(val route : String){
    object Main : NavRoutes("main_screen")
    object Trainer : NavRoutes("trainer_screen")
    object EndTrainer : NavRoutes("end_trainer_screen")
}

@Composable
fun NavHostTrainer() {

    val navHostController = rememberNavController()

    NavHost(
        navController = navHostController,
        startDestination = NavRoutes.Main.route
    ){
        composable(NavRoutes.Main.route){
            MainScreen(navHostController = navHostController)
        }
        composable(NavRoutes.Trainer.route){
            TrainerScreen(navHostController = navHostController)
        }
        composable(
            route = NavRoutes.EndTrainer.route + "/{percentCorrect}",
            arguments = listOf(
                navArgument("percentCorrect"){
                    type = NavType.IntType
                }
            ),
            enterTransition = {
                scaleIn(initialScale = 50f, animationSpec = tween(delayMillis = 500))
            },
            exitTransition = {
                scaleOut()
            }
        ){ entry ->
            val percentCorrect = entry.arguments?.getInt("percentCorrect") ?: 0
            EndTrainerScreen(
                navHostController = navHostController,
                percentCorrect = percentCorrect)
        }
    }

}