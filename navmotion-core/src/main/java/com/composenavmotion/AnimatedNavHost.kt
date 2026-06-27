package com.composenavmotion

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.composenavmotion.internal.NavMotionGraphBuilderState
import com.composenavmotion.internal.SyncNavMotionAnimationState

/**
 * A [NavHost] with global animation defaults, accessibility configuration, and animation
 * resolution for type-safe [animatedComposable] destinations.
 *
 * Animation priority (highest to lowest):
 * 1. Per-screen `animation` on [animatedComposable]
 * 2. Nested graph `animation` on [animatedNavigation]
 * 3. [defaultAnimation] on this host
 * 4. [NavMotion.fade] fallback
 *
 * @param startDestination Type-safe route instance (for example `Home`) or route string.
 * @param defaultAnimation Applied when a destination does not override animation.
 * @param config Accessibility and default timing configuration.
 */
@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier,
    defaultAnimation: NavMotionSpec = NavMotion.fade(),
    config: NavMotionConfig = NavMotionConfig(),
    builder: NavGraphBuilder.() -> Unit,
) {
    SyncNavMotionAnimationState(defaultAnimation, config)

    NavMotionGraphBuilderState.reset()
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
        builder = builder,
    )
    NavMotionGraphBuilderState.reset()
}

/**
 * Convenience overload with a top-level animations toggle.
 */
@Composable
fun AnimatedNavHost(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier,
    defaultAnimation: NavMotionSpec = NavMotion.fade(),
    animationsEnabled: Boolean,
    respectSystemAnimatorScale: Boolean = true,
    defaultDuration: Int = AnimationConfig.DEFAULT_DURATION,
    builder: NavGraphBuilder.() -> Unit,
) = AnimatedNavHost(
    navController = navController,
    startDestination = startDestination,
    modifier = modifier,
    defaultAnimation = defaultAnimation,
    config = NavMotionConfig(
        animationsEnabled = animationsEnabled,
        respectSystemAnimatorScale = respectSystemAnimatorScale,
        defaultDuration = defaultDuration,
    ),
    builder = builder,
)

/**
 * Initializes animation state for string-route usage with a plain [androidx.navigation.compose.NavHost].
 */
@Composable
fun RememberNavMotionDefaults(
    defaultAnimation: NavMotionSpec = NavMotion.fade(),
    config: NavMotionConfig = NavMotionConfig(),
) {
    SyncNavMotionAnimationState(defaultAnimation, config)
}
