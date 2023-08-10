package ru.fi.englishtrainer20.stateClasses.trainer

import androidx.compose.material3.SnackbarHostState

data class UIElementsTrainerState(
    val snackBarHostState: SnackbarHostState = SnackbarHostState(),
    val showSnackBarHost : Boolean = false
)
