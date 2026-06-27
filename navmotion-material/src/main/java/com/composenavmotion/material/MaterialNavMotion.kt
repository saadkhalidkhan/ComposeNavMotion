package com.composenavmotion.material

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import com.composenavmotion.AnimationConfig
import com.composenavmotion.NavMotion
import com.composenavmotion.NavMotionSpec

/**
 * Material-inspired navigation motion presets for [com.composenavmotion.animatedComposable].
 *
 * These presets follow common Material motion patterns using Compose Animation transitions.
 * They are simplified, navigation-friendly approximations — not full Material Motion scene
 * implementations.
 */
object MaterialNavMotion {

    fun sharedAxisX(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavMotionSpec {
        val forward = NavMotion.custom(
            enter = {
                slideInHorizontally(initialOffsetX = { it }, animationSpec = tweenSpec()) +
                    fadeIn(animationSpec = tweenSpec())
            },
            exit = {
                slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tweenSpec()) +
                    fadeOut(animationSpec = tweenSpec())
            },
            duration = duration,
            easing = easing,
        )
        val backward = NavMotion.custom(
            enter = {
                slideInHorizontally(initialOffsetX = { -it }, animationSpec = tweenSpec()) +
                    fadeIn(animationSpec = tweenSpec())
            },
            exit = {
                slideOutHorizontally(targetOffsetX = { it }, animationSpec = tweenSpec()) +
                    fadeOut(animationSpec = tweenSpec())
            },
            duration = duration,
            easing = easing,
        )
        return NavMotion.directionAware(forward = forward, backward = backward)
    }

    fun sharedAxisY(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavMotionSpec {
        val forward = NavMotion.custom(
            enter = {
                slideInVertically(initialOffsetY = { it }, animationSpec = tweenSpec()) +
                    fadeIn(animationSpec = tweenSpec())
            },
            exit = {
                slideOutVertically(targetOffsetY = { -it }, animationSpec = tweenSpec()) +
                    fadeOut(animationSpec = tweenSpec())
            },
            duration = duration,
            easing = easing,
        )
        val backward = NavMotion.custom(
            enter = {
                slideInVertically(initialOffsetY = { -it }, animationSpec = tweenSpec()) +
                    fadeIn(animationSpec = tweenSpec())
            },
            exit = {
                slideOutVertically(targetOffsetY = { it }, animationSpec = tweenSpec()) +
                    fadeOut(animationSpec = tweenSpec())
            },
            duration = duration,
            easing = easing,
        )
        return NavMotion.directionAware(forward = forward, backward = backward)
    }

    fun fadeThrough(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavMotionSpec {
        val enterDelay = duration / 4
        return NavMotion.custom(
            enter = {
                scaleIn(
                    initialScale = 0.92f,
                    animationSpec = tween(
                        durationMillis = duration,
                        delayMillis = enterDelay,
                        easing = easing,
                    ),
                ) + fadeIn(
                    animationSpec = tween(
                        durationMillis = duration,
                        delayMillis = enterDelay,
                        easing = easing,
                    ),
                )
            },
            exit = {
                fadeOut(animationSpec = tweenSpec()) +
                    scaleOut(targetScale = 0.92f, animationSpec = tweenSpec())
            },
            duration = duration,
            easing = easing,
        )
    }

    /**
     * Container transform style — a simplified Compose-friendly scale + fade transition.
     *
     * For true shared-element container transforms, pair with [com.composenavmotion.shared.SharedNavElement].
     */
    fun containerTransform(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavMotionSpec = NavMotion.custom(
        enter = {
            scaleIn(initialScale = 0.92f, animationSpec = tweenSpec()) +
                fadeIn(animationSpec = tweenSpec())
        },
        exit = {
            scaleOut(targetScale = 0.92f, animationSpec = tweenSpec()) +
                fadeOut(animationSpec = tweenSpec())
        },
        duration = duration,
        easing = easing,
    )

    fun modal(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavMotionSpec = NavMotion.custom(
        enter = {
            slideInVertically(initialOffsetY = { it }, animationSpec = tweenSpec()) +
                fadeIn(animationSpec = tweenSpec())
        },
        exit = { fadeOut(animationSpec = tweenSpec()) },
        popEnter = { fadeIn(animationSpec = tweenSpec()) },
        popExit = {
            slideOutVertically(targetOffsetY = { it }, animationSpec = tweenSpec()) +
                fadeOut(animationSpec = tweenSpec())
        },
        duration = duration,
        easing = easing,
    )
}
