package com.composenavmotion

import org.junit.Assert.assertNotNull
import org.junit.Test

class DirectionAwareNavMotionTest {
    @Test
    fun directionAware_usesBackwardForPopTransitions() {
        val forward = NavMotion.slideLeft()
        val backward = NavMotion.slideRight()
        val spec = NavMotion.directionAware(forward = forward, backward = backward)

        assertNotNull(spec.enterTransition)
        assertNotNull(spec.exitTransition)
        assertNotNull(spec.popEnterTransition)
        assertNotNull(spec.popExitTransition)
    }
}
