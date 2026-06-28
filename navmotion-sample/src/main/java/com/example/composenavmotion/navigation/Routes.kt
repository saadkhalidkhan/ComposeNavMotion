package com.example.composenavmotion.navigation

import kotlinx.serialization.Serializable

@Serializable
data object Home

@Serializable
data object ItemList

@Serializable
data class Details(val id: String)

@Serializable
data object Profile

@Serializable
data object SharedElementList

@Serializable
data class SharedProfile(val userId: String)

@Serializable
data object Settings

@Serializable
data object Modal

@Serializable
data object Sheet

@Serializable
data object AnimationSelector

@Serializable
data object CheckoutDetails

@Serializable
data object Checkout

@Serializable
data object MainGraph

@Serializable
data object NestedHome

@Serializable
data class NestedDetails(val id: String)

@Serializable
data object MaterialSharedAxisX

@Serializable
data object MaterialSharedAxisY

@Serializable
data object MaterialFadeThrough

@Serializable
data object MaterialContainerTransform

enum class DemoAnimation(val label: String) {
    Fade("Fade"),
    SlideLeft("Slide left"),
    SlideRight("Slide right"),
    SharedAxisX("Material shared axis X"),
    SharedAxisY("Material shared axis Y"),
    FadeThrough("Material fade through"),
    ContainerTransform("Material container transform"),
    Modal("Material modal"),
    None("Disabled (none)"),
}
