package ru.fi.englishtrainer20.stateClasses.trainer

import androidx.compose.animation.core.MutableTransitionState

data class AnimationTrainerState(
    val startTrainer :MutableTransitionState<Boolean> = MutableTransitionState(false),
    val shiftTargetWord : MutableTransitionState<Boolean> = MutableTransitionState(false),
    val shiftListWords : MutableTransitionState<Boolean> = MutableTransitionState(false),
    //Second value means end animation
    val backForwardTrainer : Pair<Boolean, Boolean> = Pair(false, true)
)
