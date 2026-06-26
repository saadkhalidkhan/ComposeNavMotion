package com.composenavmotion

import org.junit.Assert.assertNotEquals
import org.junit.Test

class NavMotionSpecTest {
    @Test
    fun custom_popTransitions_canDifferFromEnterExit() {
        val spec = NavMotion.custom(
            enter = { androidx.compose.animation.fadeIn(animationSpec = tweenSpec()) },
            exit = { androidx.compose.animation.fadeOut(animationSpec = tweenSpec()) },
            popEnter = { androidx.compose.animation.scaleIn(animationSpec = tweenSpec()) },
            popExit = { androidx.compose.animation.scaleOut(animationSpec = tweenSpec()) },
        )
        assertNotEquals(spec.enterTransition, spec.popEnterTransition)
        assertNotEquals(spec.exitTransition, spec.popExitTransition)
    }
}
