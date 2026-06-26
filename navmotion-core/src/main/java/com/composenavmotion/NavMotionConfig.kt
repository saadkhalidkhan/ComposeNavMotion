package com.composenavmotion

/**
 * Global animation behavior for [AnimatedNavHost].
 *
 * @param animationsEnabled When false, all destinations use [NavMotion.none].
 * @param respectSystemAnimatorScale When true, animations are disabled when the system
 *   animator duration scale is zero (accessibility "remove animations").
 * @param defaultDuration Default transition duration in milliseconds for presets.
 */
data class NavMotionConfig(
    val animationsEnabled: Boolean = true,
    val respectSystemAnimatorScale: Boolean = true,
    val defaultDuration: Int = AnimationConfig.DEFAULT_DURATION,
)
