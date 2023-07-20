package ru.fi.englishtrainer20

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.fi.englishtrainer20.ui.theme.EnglishTrainerTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EnglishTrainerTheme {

            }
        }
    }
}

@Composable
fun ScaffoldTrainer(){
    Scaffold(
        topBar = {

        },
        bottomBar = {

        },
        content = {

        }
    )
}