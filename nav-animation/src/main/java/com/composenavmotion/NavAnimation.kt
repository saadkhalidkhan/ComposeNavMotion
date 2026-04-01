package com.composenavmotion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

/**
 * Navigation transition presets for Jetpack Compose destinations.
 */
data class NavAnimation(
    val enterTransition: EnterTransition,
    val exitTransition: ExitTransition,
    val popEnterTransition: EnterTransition = enterTransition,
    val popExitTransition: ExitTransition = exitTransition,
) {
    companion object {
        private const val DEFAULT_DURATION = 300

        fun fade(duration: Int = DEFAULT_DURATION): NavAnimation {
            return NavAnimation(
                enterTransition = fadeIn(animationSpec = tween(duration)),
                exitTransition = fadeOut(animationSpec = tween(duration)),
            )
        }        fun slideUp(duration: Int = DEFAULT_DURATION): NavAnimation {
            return NavAnimation(
                enterTransition = slideInVertically(
                    initialOffsetY = { it },
                    animationSpec = tween(duration),
                ),
                exitTransition = fadeOut(animationSpec = tween(duration)),
                popEnterTransition = fadeIn(animationSpec = tween(duration)),
                popExitTransition = slideOutVertically(
                    targetOffsetY = { it },
                    animationSpec = tween(duration),
                ),
            )
        }    }
}
