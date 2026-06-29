package com.composenavmotion

import org.junit.Assert.assertNotNull
import org.junit.Test

/**
 * Ensures MVP 1–4 [NavAnimation] API remains available and delegates to [NavMotion].
 */
class NavAnimationBackwardCompatTest {
    @Test
    fun navAnimation_presetsStillWork() {
        assertNotNull(NavAnimation.fade())
        assertNotNull(NavAnimation.slideLeft())
        assertNotNull(NavAnimation.directionAware(NavAnimation.slideLeft(), NavAnimation.slideRight()))
    }
}
