package com.composenavmotion.shared

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalSharedTransitionApi
import androidx.compose.animation.SharedTransitionLayout
import androidx.compose.animation.SharedTransitionScope
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import com.composenavmotion.AnimatedNavHost
import com.composenavmotion.NavMotionConfig
import com.composenavmotion.NavMotionSpec
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

/**
 * [SharedTransitionScope] for the current navigation host, when shared transitions are enabled.
 */
val LocalSharedNavScope = compositionLocalOf<SharedTransitionScope?> { null }

/**
 * Wraps [content] in a shared-element transition when:
 * - The host uses [AnimatedNavHostWithSharedTransitions], and
 * - The destination was registered with type-safe [com.composenavmotion.animatedComposable].
 *
 * Uses Compose [ExperimentalSharedTransitionApi]. When shared transitions are unavailable,
 * [content] is rendered without shared-element behavior.
 *
 * @param key Stable key shared between the source and target screens (for example `"profile-image-$userId"`).
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun SharedNavElement(
    key: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val sharedScope = LocalSharedNavScope.current
    val animatedScope = com.composenavmotion.route.LocalSharedNavAnimatedScope.current
    if (sharedScope != null && animatedScope != null) {
        with(sharedScope) {
            Box(
                modifier = modifier.sharedElement(
                    rememberSharedContentState(key = key),
                    animatedVisibilityScope = animatedScope,
                ),
            ) {
                content()
            }
        }
    } else {
        Box(modifier = modifier) {
            content()
        }
    }
}

/**
 * [AnimatedNavHost] wrapped in [SharedTransitionLayout] so [SharedNavElement] can animate
 * between destinations.
 */
@OptIn(ExperimentalSharedTransitionApi::class)
@Composable
fun AnimatedNavHostWithSharedTransitions(
    navController: NavHostController,
    startDestination: Any,
    modifier: Modifier = Modifier,
    defaultAnimation: NavMotionSpec = com.composenavmotion.NavMotion.fade(),
    config: NavMotionConfig = NavMotionConfig(),
    builder: NavGraphBuilder.() -> Unit,
) {
    SharedTransitionLayout {
        CompositionLocalProvider(LocalSharedNavScope provides this) {
            AnimatedNavHost(
                navController = navController,
                startDestination = startDestination,
                modifier = modifier,
                defaultAnimation = defaultAnimation,
                config = config,
                builder = builder,
            )
        }
    }
}

/**
 * Read-only scope for shared-element modifiers inside [SharedNavElement].
 */
interface SharedNavScope {
    val isAvailable: Boolean
}

internal class SharedNavScopeImpl(
    override val isAvailable: Boolean,
) : SharedNavScope

@Composable
fun rememberSharedNavScope(): SharedNavScope {
    val scope = LocalSharedNavScope.current
    val animatedScope = com.composenavmotion.route.LocalSharedNavAnimatedScope.current
    return SharedNavScopeImpl(scope != null && animatedScope != null)
}
