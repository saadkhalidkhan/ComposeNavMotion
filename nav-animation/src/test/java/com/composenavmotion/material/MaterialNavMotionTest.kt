package com.composenavmotion.material

import com.composenavmotion.AnimationConfig
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNotSame
import org.junit.Test

class MaterialNavMotionTest {

    @Test
    fun sharedAxisX_buildsNonNullDirectionAwareTransitions() {
        val spec = MaterialNavMotion.sharedAxisX()
        assertNotNull(spec.enterTransition)
        assertNotNull(spec.exitTransition)
        assertNotNull(spec.popEnterTransition)
        assertNotNull(spec.popExitTransition)
    }

    @Test
    fun sharedAxisX_backNavigationReversesDirection() {
        val spec = MaterialNavMotion.sharedAxisX()
        assertNotSame(spec.enterTransition, spec.popEnterTransition)
        assertNotSame(spec.exitTransition, spec.popExitTransition)
    }

    @Test
    fun sharedAxisY_buildsNonNullDirectionAwareTransitions() {
        val spec = MaterialNavMotion.sharedAxisY()
        assertNotNull(spec.enterTransition)
        assertNotNull(spec.exitTransition)
        assertNotNull(spec.popEnterTransition)
        assertNotNull(spec.popExitTransition)
    }

    @Test
    fun sharedAxisY_backNavigationReversesDirection() {
        val spec = MaterialNavMotion.sharedAxisY()
        assertNotSame(spec.enterTransition, spec.popEnterTransition)
        assertNotSame(spec.exitTransition, spec.popExitTransition)
    }

    @Test
    fun fadeThrough_buildsPopTransitions() {
        val spec = MaterialNavMotion.fadeThrough()
        assertNotNull(spec.enterTransition)
        assertNotNull(spec.exitTransition)
        assertNotNull(spec.popEnterTransition)
        assertNotNull(spec.popExitTransition)
    }

    @Test
    fun containerTransformStyle_buildsPopTransitions() {
        val spec = MaterialNavMotion.containerTransform()
        assertNotNull(spec.enterTransition)
        assertNotNull(spec.exitTransition)
        assertNotNull(spec.popEnterTransition)
        assertNotNull(spec.popExitTransition)
    }

    @Test
    fun modal_popTransitionsDifferFromForward() {
        val spec = MaterialNavMotion.modal()
        assertNotNull(spec.enterTransition)
        assertNotNull(spec.exitTransition)
        assertNotSame(spec.enterTransition, spec.popEnterTransition)
        assertNotSame(spec.exitTransition, spec.popExitTransition)
    }

    @Test
    fun allPresets_useDefaultDuration() {
        listOf(
            MaterialNavMotion.sharedAxisX(),
            MaterialNavMotion.sharedAxisY(),
            MaterialNavMotion.fadeThrough(),
            MaterialNavMotion.containerTransform(),
            MaterialNavMotion.modal(),
        ).forEach { spec ->
            assertEquals(AnimationConfig.DEFAULT_DURATION, spec.config.duration)
        }
    }

    @Test
    fun allPresets_supportCustomDuration() {
        val duration = 400
        listOf(
            MaterialNavMotion.sharedAxisX(duration = duration),
            MaterialNavMotion.sharedAxisY(duration = duration),
            MaterialNavMotion.fadeThrough(duration = duration),
            MaterialNavMotion.containerTransform(duration = duration),
            MaterialNavMotion.modal(duration = duration),
        ).forEach { spec ->
            assertEquals(duration, spec.config.duration)
        }
    }
}
