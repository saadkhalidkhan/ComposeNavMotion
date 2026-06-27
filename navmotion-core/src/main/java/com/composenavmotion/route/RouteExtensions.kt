package com.composenavmotion.route

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.runtime.compositionLocalOf

/**
 * Provides the current destination's [AnimatedVisibilityScope] for shared-element transitions.
 *
 * Set automatically by [com.composenavmotion.animatedComposable]; consumed by
 * [com.composenavmotion.shared.SharedNavElement].
 */
val LocalSharedNavAnimatedScope = compositionLocalOf<AnimatedVisibilityScope?> { null }
