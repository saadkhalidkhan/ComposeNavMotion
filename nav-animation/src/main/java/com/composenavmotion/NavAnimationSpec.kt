package com.composenavmotion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition

/**
 * Resolved enter, exit, and pop transitions for a navigation destination.
 *
 * Use [NavAnimation.directionAware] to map separate forward and backward specs onto
 * [enterTransition]/[exitTransition] and [popEnterTransition]/[popExitTransition].
 */
data class NavAnimationSpec(
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val popEnterTransition: EnterTransition = enterTransition,
    val popExitTransition: ExitTransition = exitTransition,
    val config: AnimationConfig = AnimationConfig(),
)
