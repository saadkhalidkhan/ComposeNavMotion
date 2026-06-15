package com.composenavmotion

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable

/**
 * Registers a composable destination with enter, exit, and pop transitions from [animation].
 *
 * Pop transitions power back navigation. Pair with [NavAnimation.directionAware] so forward
 * pushes and backward pops use different visual directions.
 */
fun NavGraphBuilder.animatedComposable(
    route: String,
    animation: NavAnimationSpec = NavAnimation.fade(),
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        enterTransition = { animation.enterTransition },
        exitTransition = { animation.exitTransition },
        popEnterTransition = { animation.popEnterTransition },
        popExitTransition = { animation.popExitTransition },
        content = content,
    )
}
