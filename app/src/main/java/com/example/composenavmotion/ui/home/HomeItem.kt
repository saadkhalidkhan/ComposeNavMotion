package com.example.composenavmotion.ui.home

data class HomeItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val opensSheet: Boolean = false,
    val opensCheckoutFlow: Boolean = false,
)

val sampleHomeItems = listOf(
    HomeItem("fade", "Fade transition", "NavAnimation.fade() preset"),
    HomeItem("slide-left", "Slide left transition", "NavAnimation.slideLeft() preset"),
    HomeItem("slide-right", "Slide right transition", "NavAnimation.slideRight() preset"),
    HomeItem("scale", "Scale transition", "NavAnimation.scale() preset"),
    HomeItem(
        "checkout-flow",
        "Direction-aware checkout",
        "Home → Details → Checkout with slideLeft / slideRight",
        opensCheckoutFlow = true,
    ),
    HomeItem("sheet", "Sheet transition", "Custom slide up with fade", opensSheet = true),
)
