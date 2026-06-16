package com.composenavmotion

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically

object NavAnimation {
    fun fade(duration: Int = AnimationConfig.DEFAULT_DURATION, easing: Easing = FastOutSlowInEasing) = custom(
        enter = { fadeIn(animationSpec = tweenSpec()) },
        exit = { fadeOut(animationSpec = tweenSpec()) },
        duration = duration, easing = easing,
    )
    fun slideLeft(duration: Int = AnimationConfig.DEFAULT_DURATION, easing: Easing = FastOutSlowInEasing) = custom(
        enter = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tweenSpec()) },
        exit = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tweenSpec()) },
        popEnter = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tweenSpec()) },
        popExit = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tweenSpec()) },
        duration = duration, easing = easing,
    )
    fun slideRight(duration: Int = AnimationConfig.DEFAULT_DURATION, easing: Easing = FastOutSlowInEasing) = custom(
        enter = { slideInHorizontally(initialOffsetX = { -it }, animationSpec = tweenSpec()) },
        exit = { slideOutHorizontally(targetOffsetX = { it }, animationSpec = tweenSpec()) },
        popEnter = { slideInHorizontally(initialOffsetX = { it }, animationSpec = tweenSpec()) },
        popExit = { slideOutHorizontally(targetOffsetX = { -it }, animationSpec = tweenSpec()) },
        duration = duration, easing = easing,
    )
    fun slideUp(duration: Int = AnimationConfig.DEFAULT_DURATION, easing: Easing = FastOutSlowInEasing) = custom(
        enter = { slideInVertically(initialOffsetY = { it }, animationSpec = tweenSpec()) },
        exit = { fadeOut(animationSpec = tweenSpec()) },
        popEnter = { fadeIn(animationSpec = tweenSpec()) },
        popExit = { slideOutVertically(targetOffsetY = { it }, animationSpec = tweenSpec()) },
        duration = duration, easing = easing,
    )
    fun scale(duration: Int = AnimationConfig.DEFAULT_DURATION, easing: Easing = FastOutSlowInEasing) = custom(
        enter = { scaleIn(animationSpec = tweenSpec()) + fadeIn(animationSpec = tweenSpec()) },
        exit = { scaleOut(animationSpec = tweenSpec()) + fadeOut(animationSpec = tweenSpec()) },
        duration = duration, easing = easing,
    )
    fun custom(
        enter: AnimationConfig.() -> EnterTransition,
        exit: AnimationConfig.() -> ExitTransition,
        popEnter: AnimationConfig.() -> EnterTransition = enter,
        popExit: AnimationConfig.() -> ExitTransition = exit,
        duration: Int = AnimationConfig.DEFAULT_DURATION,
        easing: Easing = FastOutSlowInEasing,
    ): NavAnimationSpec {
        val config = AnimationConfig(duration, easing)
        return NavAnimationSpec(config.enter(), config.exit(), config.popEnter(), config.popExit(), config)
    }

    /**
     * Combines separate forward and backward [NavAnimationSpec] instances for direction-aware navigation.
     *
     * Forward navigation (push) uses [forward] enter and exit transitions.
     * Back navigation (pop) uses [backward] enter and exit transitions as pop enter and pop exit.
     *
     * Example:
     * ```
     * val animation = NavAnimation.directionAware(
     *     forward = NavAnimation.slideLeft(),
     *     backward = NavAnimation.slideRight(),
     * )
     * ```
     */
    fun directionAware(
        forward: NavAnimationSpec,
        backward: NavAnimationSpec,
    ): NavAnimationSpec = NavAnimationSpec(
        enterTransition = forward.enterTransition,
        exitTransition = forward.exitTransition,
        popEnterTransition = backward.enterTransition,
        popExitTransition = backward.exitTransition,
        config = forward.config,
    )
}
