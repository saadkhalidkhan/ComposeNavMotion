package com.example.composenavmotion.ui.home

data class HomeItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val opensSheet: Boolean = false,
    val opensCheckoutFlow: Boolean = false,
    val opensList: Boolean = false,
    val opensAnimationSelector: Boolean = false,
    val opensSharedElements: Boolean = false,
    val opensNestedGraph: Boolean = false,
    val opensSettings: Boolean = false,
    val opensModal: Boolean = false,
    val materialRoute: String? = null,
)

val sampleHomeItems = listOf(
    HomeItem("fade", "Fade transition", "NavMotion.fade() preset"),
    HomeItem("slide-left", "Slide left transition", "NavMotion.slideLeft() preset"),
    HomeItem("slide-right", "Slide right transition", "NavMotion.slideRight() preset"),
    HomeItem("scale", "Scale transition", "NavMotion.scale() preset"),
    HomeItem(
        "item-list",
        "List screen",
        "Browse all demo items with type-safe routes",
        opensList = true,
    ),
    HomeItem(
        "animation-selector",
        "Animation selector",
        "Change the global default animation at runtime",
        opensAnimationSelector = true,
    ),
    HomeItem(
        "shared-elements",
        "Shared element example",
        "SharedNavElement profile avatar transitions",
        opensSharedElements = true,
    ),
    HomeItem(
        "nested-graph",
        "Nested graph example",
        "animatedNavigation<MainGraph> with graph-level animation",
        opensNestedGraph = true,
    ),
    HomeItem(
        "checkout-flow",
        "Direction-aware checkout",
        "Home → Details → Checkout with slideLeft / slideRight",
        opensCheckoutFlow = true,
    ),
    HomeItem("sheet", "Sheet transition", "Custom slide up with fade", opensSheet = true),
    HomeItem(
        "settings",
        "Settings screen",
        "Type-safe Settings route",
        opensSettings = true,
    ),
    HomeItem(
        "modal-screen",
        "Modal screen",
        "MaterialNavMotion.modal() preset",
        opensModal = true,
    ),
    HomeItem(
        "material-shared-axis-x",
        "Material: Shared Axis X",
        "MaterialNavMotion.sharedAxisX()",
        materialRoute = "material-shared-axis-x",
    ),
    HomeItem(
        "material-shared-axis-y",
        "Material: Shared Axis Y",
        "MaterialNavMotion.sharedAxisY()",
        materialRoute = "material-shared-axis-y",
    ),
    HomeItem(
        "material-fade-through",
        "Material: Fade Through",
        "MaterialNavMotion.fadeThrough()",
        materialRoute = "material-fade-through",
    ),
    HomeItem(
        "material-container-transform",
        "Material: Container Transform Style",
        "MaterialNavMotion.containerTransform()",
        materialRoute = "material-container-transform",
    ),
)
