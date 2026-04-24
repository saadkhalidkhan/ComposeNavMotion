package com.composenavmotion

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import org.junit.Assert.assertNotSame
import org.junit.Test

class NavAnimationSpecTest {
    @Test fun custom_popTransitions_canDiffer() {
        val spec = NavAnimation.custom(
            enter = { slideInHorizontally(animationSpec = tweenSpec()) },
            exit = { slideOutHorizontally(animationSpec = tweenSpec()) },
            popEnter = { fadeIn(animationSpec = tweenSpec()) },
            popExit = { fadeOut(animationSpec = tweenSpec()) },
        )
        assertNotSame(spec.enterTransition, spec.popEnterTransition)
    }
}
