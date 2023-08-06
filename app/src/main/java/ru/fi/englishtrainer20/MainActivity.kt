package ru.fi.englishtrainer20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import ru.fi.englishtrainer20.navigation.NavHostTrainer
import ru.fi.englishtrainer20.ui.theme.EnglishTrainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishTrainerTheme {
                ScaffoldTrainer()
            }
        }

    }
}

@Composable
fun ScaffoldTrainer(){
    Scaffold(
        content = {
            NavHostTrainer()
        }
    )
}