package ru.fi.englishtrainer20.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.fi.englishtrainer20.screens.MainScreen
import ru.fi.englishtrainer20.screens.TrainerScreen

sealed class NavRoutes(val route : String){
    object Main : NavRoutes("main_screen")
    object Trainer : NavRoutes("trainer_screen")

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
    }

}