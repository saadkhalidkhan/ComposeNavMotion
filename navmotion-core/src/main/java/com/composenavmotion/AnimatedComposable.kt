package com.composenavmotion

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.composenavmotion.internal.NavMotionAnimationState
import com.composenavmotion.internal.NavMotionGraphBuilderState
import com.composenavmotion.route.LocalSharedNavAnimatedScope
import kotlin.reflect.KType

/**
 * Registers a type-safe composable destination with resolved enter, exit, and pop transitions.
 *
 * Requires Kotlin Serialization on the route type. Use inside [AnimatedNavHost] for global
 * defaults and accessibility configuration.
 */
inline fun <reified T : Any> NavGraphBuilder.animatedComposable(
    animation: NavMotionSpec? = null,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline content: @Composable (NavBackStackEntry) -> Unit,
) {
    val capturedGraphAnimation = NavMotionGraphBuilderState.peekGraphAnimation()

    composable<T>(
        typeMap = typeMap,
        enterTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).enterTransition
        },
        exitTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).exitTransition
        },
        popEnterTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).popEnterTransition
        },
        popExitTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).popExitTransition
        },
    ) { entry ->
        CompositionLocalProvider(LocalSharedNavAnimatedScope provides this) {
            content(entry)
        }
    }
}

/**
 * Registers a nested navigation graph with an optional animation override for its destinations.
 */
inline fun <reified T : Any> NavGraphBuilder.animatedNavigation(
    startDestination: Any,
    animation: NavMotionSpec? = null,
    typeMap: Map<KType, @JvmSuppressWildcards NavType<*>> = emptyMap(),
    noinline builder: NavGraphBuilder.() -> Unit,
) {
    NavMotionGraphBuilderState.pushGraphAnimation(animation)
    navigation<T>(
        startDestination = startDestination,
        typeMap = typeMap,
        builder = builder,
    )
    NavMotionGraphBuilderState.popGraphAnimation()
}

/**
 * Registers a string-route composable destination (MVP 1–4 compatible API).
 *
 * Pair with [RememberNavMotionDefaults] when using a plain [androidx.navigation.compose.NavHost].
 */
fun NavGraphBuilder.animatedComposable(
    route: String,
    animation: NavMotionSpec = NavMotion.fade(),
    arguments: List<androidx.navigation.NamedNavArgument> = emptyList(),
    deepLinks: List<androidx.navigation.NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    val capturedGraphAnimation = NavMotionGraphBuilderState.peekGraphAnimation()

    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).enterTransition
        },
        exitTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).exitTransition
        },
        popEnterTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).popEnterTransition
        },
        popExitTransition = {
            NavMotionAnimationState.resolve(animation, capturedGraphAnimation).popExitTransition
        },
        content = content,
    )
}
