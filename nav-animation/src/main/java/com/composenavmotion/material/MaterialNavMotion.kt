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
import com.composenavmotion.NavAnimation
import com.composenavmotion.NavAnimationSpec

/**
 * Material-inspired navigation motion presets for [com.composenavmotion.animatedComposable].
 *
 * These presets follow common Material motion patterns using Compose Animation transitions.
 * They are simplified, navigation-friendly approximations — not full Material Motion scene
 * implementations.
 *
 * Example:
 * ```
 * animatedComposable(
 *     route = "details",
 *     animation = MaterialNavMotion.sharedAxisX(),
 * ) {
 *     DetailsScreen()
 * }
 * ```
 */
object MaterialNavMotion {

    /**
     * Shared axis along the X axis — use for lateral navigation such as peer destinations.
     *
     * Forward: the new screen slides in from the right and fades in; the old screen slides
     * out to the left and fades out.
     *
     * Back: the previous screen slides in from the left and fades in; the current screen
     * slides out to the right and fades out.
     */
    fun sharedAxisX(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavAnimationSpec {
        val forward = NavAnimation.custom(
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
        val backward = NavAnimation.custom(
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
        return NavAnimation.directionAware(forward = forward, backward = backward)
    }

    /**
     * Shared axis along the Y axis — use for vertical navigation such as parent/child flows.
     *
     * Forward: the new screen slides in from the bottom and fades in; the old screen slides
     * out to the top and fades out.
     *
     * Back: the previous screen slides in from the top and fades in; the current screen
     * slides out to the bottom and fades out.
     */
    fun sharedAxisY(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavAnimationSpec {
        val forward = NavAnimation.custom(
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
        val backward = NavAnimation.custom(
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
        return NavAnimation.directionAware(forward = forward, backward = backward)
    }

    /**
     * Fade through — use when navigating between unrelated destinations at the same level.
     *
     * The outgoing screen fades out quickly with a subtle scale-down. The incoming screen
     * fades in slightly later with a subtle scale-up. Back navigation uses the same pattern.
     */
    fun fadeThrough(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavAnimationSpec {
        val enterDelay = duration / 4
        return NavAnimation.custom(
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
     * This is **not** a true Material container transform (which typically requires shared
     * element / shared bounds support). The incoming screen fades in and scales up slightly;
     * the outgoing screen fades out and scales down slightly.
     */
    fun containerTransform(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavAnimationSpec = NavAnimation.custom(
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

    /**
     * Modal / bottom sheet style — the new screen enters from the bottom and exits to the
     * bottom. The outgoing screen fades out on forward navigation and fades back in on pop.
     */
    fun modal(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavAnimationSpec = NavAnimation.custom(
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
