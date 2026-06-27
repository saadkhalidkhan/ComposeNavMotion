package com.composenavmotion.material

import com.composenavmotion.AnimationConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Test

class MaterialNavMotionTest {
    @Test
    fun allPresets_buildSpecs() {
        assertNotNull(MaterialNavMotion.sharedAxisX())
        assertNotNull(MaterialNavMotion.sharedAxisY())
        assertNotNull(MaterialNavMotion.fadeThrough())
        assertNotNull(MaterialNavMotion.containerTransform())
        assertNotNull(MaterialNavMotion.modal())
    }

    @Test
    fun sharedAxisX_respectsCustomDuration() {
        val spec = MaterialNavMotion.sharedAxisX(duration = 400)
        assertEquals(400, spec.config.duration)
    }

    @Test
    fun defaultDuration_is300ms() {
        val spec = MaterialNavMotion.fadeThrough()
        assertEquals(AnimationConfig.DEFAULT_DURATION, spec.config.duration)
    }
}
