package com.composenavmotion

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing

/**
 * Backward-compatible alias for [NavMotion] (MVP 1–4 API).
 */
@Deprecated(
    message = "Use NavMotion instead",
    replaceWith = ReplaceWith("NavMotion"),
)
object NavAnimation {
    fun fade(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ) = NavMotion.fade(duration, easing)

    fun slideLeft(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ) = NavMotion.slideLeft(duration, easing)

    fun slideRight(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ) = NavMotion.slideRight(duration, easing)

    fun slideUp(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ) = NavMotion.slideUp(duration, easing)

    fun scale(
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ) = NavMotion.scale(duration, easing)

    fun custom(
        enter: AnimationConfig.() -> androidx.compose.animation.EnterTransition,
        exit: AnimationConfig.() -> androidx.compose.animation.ExitTransition,
        popEnter: AnimationConfig.() -> androidx.compose.animation.EnterTransition = enter,
        popExit: AnimationConfig.() -> androidx.compose.animation.ExitTransition = exit,
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ) = NavMotion.custom(enter, exit, popEnter, popExit, duration, easing)

    fun directionAware(
        forward: NavMotionSpec,
        backward: NavMotionSpec,
    ) = NavMotion.directionAware(forward, backward)
}

/**
 * Backward-compatible alias for [NavMotionSpec] (MVP 1–4 API).
 */
@Deprecated(
    message = "Use NavMotionSpec instead",
    replaceWith = ReplaceWith("NavMotionSpec"),
)
typealias NavAnimationSpec = NavMotionSpec
