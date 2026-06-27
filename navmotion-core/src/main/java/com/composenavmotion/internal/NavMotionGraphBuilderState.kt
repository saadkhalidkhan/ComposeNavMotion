package com.composenavmotion.internal

import com.composenavmotion.NavMotion
import com.composenavmotion.NavMotionConfig
import com.composenavmotion.NavMotionSpec

/**
 * Tracks nested-graph animation overrides while a [androidx.navigation.NavGraphBuilder] is built.
 */
@PublishedApi
internal object NavMotionGraphBuilderState {
    private val graphAnimationStack = ArrayDeque<NavMotionSpec?>()

    fun pushGraphAnimation(animation: NavMotionSpec?) {
        graphAnimationStack.addLast(animation)
    }

    fun popGraphAnimation() {
        if (graphAnimationStack.isNotEmpty()) {
            graphAnimationStack.removeLast()
        }
    }

    fun peekGraphAnimation(): NavMotionSpec? = graphAnimationStack.lastOrNull()

    fun reset() {
        graphAnimationStack.clear()
    }
}

/**
 * Host-level animation state readable from non-composable navigation transition lambdas.
 */
@PublishedApi
internal object NavMotionAnimationState {
    var hostDefaultAnimation: NavMotionSpec = NavMotion.fade()
    var config: NavMotionConfig = NavMotionConfig()
    var animatorDurationScale: Float = 1f

    fun resolve(
        perScreen: NavMotionSpec?,
        capturedGraphAnimation: NavMotionSpec?,
    ): NavMotionSpec {
        val base = perScreen
            ?: capturedGraphAnimation
            ?: hostDefaultAnimation

        if (!config.animationsEnabled) {
            return NavMotion.none()
        }

        if (config.respectSystemAnimatorScale && animatorDurationScale == 0f) {
            return NavMotion.none()
        }

        return base
    }

    fun reset() {
        hostDefaultAnimation = NavMotion.fade()
        config = NavMotionConfig()
        animatorDurationScale = 1f
    }
}
