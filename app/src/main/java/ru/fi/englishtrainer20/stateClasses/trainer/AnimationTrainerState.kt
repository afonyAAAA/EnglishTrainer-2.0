package ru.fi.englishtrainer20.stateClasses.trainer

import androidx.compose.animation.core.MutableTransitionState

data class AnimationTrainerState(
    val startTrainer : Boolean = false,
    val shiftTargetWord : MutableTransitionState<Boolean> = MutableTransitionState(false),
    val shiftListWords : MutableTransitionState<Boolean> = MutableTransitionState(false)
)
