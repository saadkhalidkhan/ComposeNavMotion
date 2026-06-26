package com.composenavmotion

import org.junit.Assert.assertEquals
import org.junit.Test

class AnimationConfigTest {
    @Test
    fun defaultDuration_is300ms() {
        assertEquals(300, AnimationConfig.DEFAULT_DURATION)
        assertEquals(300, AnimationConfig().duration)
    }
}
