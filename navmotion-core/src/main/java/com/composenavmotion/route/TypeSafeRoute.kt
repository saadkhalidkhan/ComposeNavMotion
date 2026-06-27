package com.composenavmotion.route

/**
 * Marker documentation for Kotlin Serialization route types used with type-safe navigation.
 *
 * Annotate routes with `@Serializable`:
 * ```
 * @Serializable
 * object Home
 *
 * @Serializable
 * data class Details(val id: String)
 * ```
 *
 * Retrieve arguments with Navigation's `toRoute<T>()`:
 * ```
 * val details = backStackEntry.toRoute<Details>()
 * ```
 */
interface TypeSafeRoute
