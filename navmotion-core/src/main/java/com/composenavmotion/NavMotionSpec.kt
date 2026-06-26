package com.composenavmotion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

/**
 * Resolved enter, exit, and pop transitions for a navigation destination.
 *
 * Use [NavMotion.directionAware] to map separate forward and backward specs onto
 * [enterTransition]/[exitTransition] and [popEnterTransition]/[popExitTransition].
 */
data class NavMotionSpec(
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val popEnterTransition: EnterTransition = enterTransition,
    val popExitTransition: ExitTransition = exitTransition,
    val config: AnimationConfig = AnimationConfig(),
)
