package com.composenavmotion

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import org.junit.Assert.assertNotNull
import org.junit.Test

class NavAnimationTest {
    @Test fun directionAware_slidePreset_mapsForwardAndBackward() {
        val spec = NavAnimation.directionAware(
            forward = NavAnimation.slideLeft(),
            backward = NavAnimation.slideRight(),
        )
        assertNotNull(spec.popExitTransition)
    }

    @Test fun custom_mixedAnimation_buildsSpec() {
        val spec = NavAnimation.custom(
            enter = { slideInVertically(animationSpec = tweenSpec()) },
            exit = { fadeOut(animationSpec = tweenSpec()) },
            popEnter = { fadeIn(animationSpec = tweenSpec()) },
            popExit = { slideOutVertically(animationSpec = tweenSpec()) },
        )
        assertNotNull(spec.popExitTransition)
    }
}
