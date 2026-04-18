package com.composenavmotion

import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.tween

data class AnimationConfig(
    val duration: Int = DEFAULT_DURATION,
    val easing: Easing = FastOutSlowInEasing,
) {
    fun <T> tweenSpec(): FiniteAnimationSpec<T> = tween(durationMillis = duration, easing = easing)
    companion object { const val DEFAULT_DURATION = 300 }
}
