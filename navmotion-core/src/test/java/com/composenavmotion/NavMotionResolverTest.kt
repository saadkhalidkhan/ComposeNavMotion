package com.composenavmotion

import com.composenavmotion.internal.NavMotionAnimationState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class NavMotionResolverTest {
    @Before
    fun resetState() {
        NavMotionAnimationState.reset()
    }

    @Test
    fun disabledAnimations_returnNoneTransitions() {
        NavMotionAnimationState.hostDefaultAnimation = NavMotion.fade()
        NavMotionAnimationState.config = NavMotionConfig(animationsEnabled = false)

        val resolved = NavMotionAnimationState.resolve(
            perScreen = NavMotion.slideLeft(),
            capturedGraphAnimation = NavMotion.scale(),
        )
        assertEquals(androidx.compose.animation.EnterTransition.None, resolved.enterTransition)
        assertEquals(androidx.compose.animation.ExitTransition.None, resolved.exitTransition)
    }

    @Test
    fun perScreenOverridesGlobalDefault() {
        NavMotionAnimationState.config = NavMotionConfig()
        NavMotionAnimationState.hostDefaultAnimation = NavMotion.fade()
        val perScreen = NavMotion.slideLeft()

        val resolved = NavMotionAnimationState.resolve(perScreen, null)
        assertEquals(perScreen.enterTransition, resolved.enterTransition)
    }

    @Test
    fun nestedGraphOverridesGlobalDefault() {
        NavMotionAnimationState.config = NavMotionConfig()
        NavMotionAnimationState.hostDefaultAnimation = NavMotion.fade()
        val graph = NavMotion.slideRight()

        val resolved = NavMotionAnimationState.resolve(null, graph)
        assertEquals(graph.enterTransition, resolved.enterTransition)
    }

    @Test
    fun globalDefaultUsedWhenNoOverrides() {
        val default = NavMotion.scale()
        NavMotionAnimationState.config = NavMotionConfig()
        NavMotionAnimationState.hostDefaultAnimation = default

        val resolved = NavMotionAnimationState.resolve(null, null)
        assertEquals(default.enterTransition, resolved.enterTransition)
    }

    @Test
    fun perScreenOverridesNestedGraph() {
        NavMotionAnimationState.config = NavMotionConfig()
        NavMotionAnimationState.hostDefaultAnimation = NavMotion.fade()
        val perScreen = NavMotion.slideUp()
        val graph = NavMotion.slideRight()

        val resolved = NavMotionAnimationState.resolve(perScreen, graph)
        assertEquals(perScreen.enterTransition, resolved.enterTransition)
    }

    @Test
    fun zeroAnimatorScale_disablesAnimationsWhenRespected() {
        NavMotionAnimationState.config = NavMotionConfig(
            animationsEnabled = true,
            respectSystemAnimatorScale = true,
        )
        NavMotionAnimationState.hostDefaultAnimation = NavMotion.fade()
        NavMotionAnimationState.animatorDurationScale = 0f

        val resolved = NavMotionAnimationState.resolve(null, null)
        assertEquals(androidx.compose.animation.EnterTransition.None, resolved.enterTransition)
    }
}
