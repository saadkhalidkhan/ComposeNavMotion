package com.example.composenavmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.composenavmotion.NavMotion
import com.composenavmotion.NavMotionConfig
import com.composenavmotion.animatedComposable
import com.composenavmotion.animatedNavigation
import com.composenavmotion.material.MaterialNavMotion
import com.composenavmotion.shared.AnimatedNavHostWithSharedTransitions
import com.example.composenavmotion.navigation.AnimationSelector
import com.example.composenavmotion.navigation.Checkout
import com.example.composenavmotion.navigation.CheckoutDetails
import com.example.composenavmotion.navigation.DemoAnimation
import com.example.composenavmotion.navigation.Details
import com.example.composenavmotion.navigation.Home
import com.example.composenavmotion.navigation.ItemList
import com.example.composenavmotion.navigation.MainGraph
import com.example.composenavmotion.navigation.Modal
import com.example.composenavmotion.navigation.NestedDetails
import com.example.composenavmotion.navigation.NestedHome
import com.example.composenavmotion.navigation.MaterialContainerTransform
import com.example.composenavmotion.navigation.MaterialFadeThrough
import com.example.composenavmotion.navigation.MaterialSharedAxisX
import com.example.composenavmotion.navigation.MaterialSharedAxisY
import com.example.composenavmotion.navigation.SharedElementList
import com.example.composenavmotion.navigation.Profile
import com.example.composenavmotion.navigation.Settings
import com.example.composenavmotion.navigation.SharedProfile
import com.example.composenavmotion.navigation.Sheet
import com.example.composenavmotion.ui.checkout.CheckoutScreen
import com.example.composenavmotion.ui.detail.DetailScreen
import com.example.composenavmotion.ui.home.HomeScreen
import com.example.composenavmotion.ui.list.ListScreen
import com.example.composenavmotion.ui.modal.ModalScreen
import com.example.composenavmotion.ui.nested.NestedDetailsScreen
import com.example.composenavmotion.ui.nested.NestedHomeScreen
import com.example.composenavmotion.ui.profile.ProfileScreen
import com.example.composenavmotion.ui.selector.AnimationSelectorScreen
import com.example.composenavmotion.ui.settings.SettingsScreen
import com.example.composenavmotion.ui.shared.SharedElementListScreen
import com.example.composenavmotion.ui.shared.SharedProfileDetailScreen
import com.example.composenavmotion.ui.sheet.SheetScreen
import com.example.composenavmotion.ui.theme.ComposeNavMotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavMotionTheme {
                val navController = rememberNavController()
                var selectedAnimation by remember { mutableStateOf(DemoAnimation.SharedAxisX) }

                val defaultAnimation = remember(selectedAnimation) {
                    selectedAnimation.toNavMotionSpec()
                }
                val directionAwareAnimation = remember {
                    NavMotion.directionAware(
                        forward = NavMotion.slideLeft(),
                        backward = NavMotion.slideRight(),
                    )
                }

                Scaffold { innerPadding ->
                    AnimatedNavHostWithSharedTransitions(
                        navController = navController,
                        startDestination = Home,
                        modifier = Modifier.padding(innerPadding),
                        defaultAnimation = defaultAnimation,
                        config = NavMotionConfig(
                            animationsEnabled = selectedAnimation != DemoAnimation.None,
                            respectSystemAnimatorScale = true,
                        ),
                    ) {
                        animatedComposable<Home> {
                            HomeScreen(
                                onOpenDetail = { item ->
                                    navController.navigate(Details(item.id))
                                },
                                onOpenSheet = { navController.navigate(Sheet) },
                                onOpenProfile = { navController.navigate(Profile) },
                                onOpenCheckoutFlow = { navController.navigate(CheckoutDetails) },
                                onOpenList = { navController.navigate(ItemList) },
                                onOpenAnimationSelector = { navController.navigate(AnimationSelector) },
                                onOpenSharedElements = { navController.navigate(SharedElementList) },
                                onOpenNestedGraph = { navController.navigate(NestedHome) },
                                onOpenSettings = { navController.navigate(Settings) },
                                onOpenModal = { navController.navigate(Modal) },
                                onOpenMaterialRoute = { routeId ->
                                    when (routeId) {
                                        "material-shared-axis-x" -> navController.navigate(MaterialSharedAxisX)
                                        "material-shared-axis-y" -> navController.navigate(MaterialSharedAxisY)
                                        "material-fade-through" -> navController.navigate(MaterialFadeThrough)
                                        "material-container-transform" -> navController.navigate(MaterialContainerTransform)
                                        else -> navController.navigate(Details(routeId))
                                    }
                                },
                            )
                        }

                        animatedComposable<ItemList> {
                            ListScreen(
                                onBack = { navController.popBackStack() },
                                onOpenDetail = { id -> navController.navigate(Details(id)) },
                            )
                        }

                        animatedComposable<AnimationSelector> {
                            AnimationSelectorScreen(
                                selected = selectedAnimation,
                                onBack = { navController.popBackStack() },
                                onSelect = { animation ->
                                    selectedAnimation = animation
                                    navController.popBackStack()
                                },
                            )
                        }

                        animatedComposable<Details>(
                            animation = MaterialNavMotion.containerTransform(),
                        ) { entry ->
                            val details = entry.toRoute<Details>()
                            DetailScreen(
                                itemId = details.id,
                                onBack = { navController.popBackStack() },
                                animationDescription = "Per-screen MaterialNavMotion.containerTransform() override",
                            )
                        }

                        animatedComposable<CheckoutDetails>(animation = directionAwareAnimation) {
                            DetailScreen(
                                itemId = "checkout-flow",
                                onBack = { navController.popBackStack() },
                                onContinueToCheckout = { navController.navigate(Checkout) },
                            )
                        }

                        animatedComposable<Checkout>(animation = directionAwareAnimation) {
                            CheckoutScreen(onBack = { navController.popBackStack() })
                        }

                        animatedComposable<Profile> {
                            ProfileScreen(onBack = { navController.popBackStack() })
                        }

                        animatedComposable<SharedElementList> {
                            SharedElementListScreen(
                                onBack = { navController.popBackStack() },
                                onOpenProfile = { userId ->
                                    navController.navigate(SharedProfile(userId))
                                },
                            )
                        }

                        animatedComposable<SharedProfile>(
                            animation = MaterialNavMotion.containerTransform(),
                        ) { entry ->
                            val profile = entry.toRoute<SharedProfile>()
                            SharedProfileDetailScreen(
                                userId = profile.userId,
                                onBack = { navController.popBackStack() },
                            )
                        }

                        animatedComposable<Settings>(
                            animation = MaterialNavMotion.sharedAxisY(),
                        ) {
                            SettingsScreen(onBack = { navController.popBackStack() })
                        }

                        animatedComposable<Modal>(
                            animation = MaterialNavMotion.modal(),
                        ) {
                            ModalScreen(onBack = { navController.popBackStack() })
                        }

                        animatedComposable<Sheet>(
                            animation = NavMotion.custom(
                                enter = { slideInVertically(animationSpec = tweenSpec()) },
                                exit = { fadeOut(animationSpec = tweenSpec()) },
                                popEnter = { fadeIn(animationSpec = tweenSpec()) },
                                popExit = { slideOutVertically(animationSpec = tweenSpec()) },
                                duration = 350,
                            ),
                        ) {
                            SheetScreen(onBack = { navController.popBackStack() })
                        }

                        animatedNavigation<MainGraph>(
                            startDestination = NestedHome,
                            animation = MaterialNavMotion.sharedAxisY(),
                        ) {
                            animatedComposable<NestedHome> {
                                NestedHomeScreen(
                                    onBack = { navController.popBackStack() },
                                    onOpenDetails = {
                                        navController.navigate(NestedDetails("nested-item"))
                                    },
                                )
                            }
                            animatedComposable<NestedDetails> { entry ->
                                val args = entry.toRoute<NestedDetails>()
                                NestedDetailsScreen(
                                    id = args.id,
                                    onBack = { navController.popBackStack() },
                                )
                            }
                        }

                        animatedComposable<MaterialSharedAxisX>(
                            animation = MaterialNavMotion.sharedAxisX(),
                        ) {
                            DetailScreen(
                                itemId = "material-shared-axis-x",
                                onBack = { navController.popBackStack() },
                                animationDescription = "MaterialNavMotion.sharedAxisX()",
                            )
                        }

                        animatedComposable<MaterialSharedAxisY>(
                            animation = MaterialNavMotion.sharedAxisY(),
                        ) {
                            SettingsScreen(onBack = { navController.popBackStack() })
                        }

                        animatedComposable<MaterialFadeThrough>(
                            animation = MaterialNavMotion.fadeThrough(),
                        ) {
                            DetailScreen(
                                itemId = "material-fade-through",
                                onBack = { navController.popBackStack() },
                                animationDescription = "MaterialNavMotion.fadeThrough()",
                            )
                        }

                        animatedComposable<MaterialContainerTransform>(
                            animation = MaterialNavMotion.containerTransform(),
                        ) {
                            DetailScreen(
                                itemId = "material-container-transform",
                                onBack = { navController.popBackStack() },
                                animationDescription = "MaterialNavMotion.containerTransform()",
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun DemoAnimation.toNavMotionSpec() = when (this) {
    DemoAnimation.Fade -> NavMotion.fade()
    DemoAnimation.SlideLeft -> NavMotion.slideLeft()
    DemoAnimation.SlideRight -> NavMotion.slideRight()
    DemoAnimation.SharedAxisX -> MaterialNavMotion.sharedAxisX()
    DemoAnimation.SharedAxisY -> MaterialNavMotion.sharedAxisY()
    DemoAnimation.FadeThrough -> MaterialNavMotion.fadeThrough()
    DemoAnimation.ContainerTransform -> MaterialNavMotion.containerTransform()
    DemoAnimation.Modal -> MaterialNavMotion.modal()
    DemoAnimation.None -> NavMotion.none()
}
