package ru.fi.englishtrainer20.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavActionBuilder
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController

@Composable
fun NavHostTrainer(){

    val navHostController = rememberNavController()

    NavHost(navController = navHostController, startDestination = ""){

    }

}