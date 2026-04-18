package com.composenavmotion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

data class NavAnimationSpec(
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val popEnterTransition: EnterTransition = enterTransition,
    val popExitTransition: ExitTransition = exitTransition,
    val config: AnimationConfig = AnimationConfig(),
)
