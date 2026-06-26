package com.composenavmotion

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import org.junit.Assert.assertNotNull
import org.junit.Test

class NavMotionTest {
    @Test
    fun directionAware_slidePreset_mapsForwardAndBackward() {
        val spec = NavMotion.directionAware(
            forward = NavMotion.slideLeft(),
            backward = NavMotion.slideRight(),
        )
        assertNotNull(spec.popExitTransition)
    }

    @Test
    fun custom_mixedAnimation_buildsSpec() {
        val spec = NavMotion.custom(
            enter = { slideInVertically(animationSpec = tweenSpec()) },
            exit = { fadeOut(animationSpec = tweenSpec()) },
            popEnter = { fadeIn(animationSpec = tweenSpec()) },
            popExit = { slideOutVertically(animationSpec = tweenSpec()) },
        )
        assertNotNull(spec.popExitTransition)
    }

    @Test
    fun none_returnsNoTransitions() {
        val spec = NavMotion.none()
        assertEquals(androidx.compose.animation.EnterTransition.None, spec.enterTransition)
        assertEquals(androidx.compose.animation.ExitTransition.None, spec.exitTransition)
    }

    private fun assertEquals(expected: Any, actual: Any) {
        org.junit.Assert.assertEquals(expected, actual)
    }
}
