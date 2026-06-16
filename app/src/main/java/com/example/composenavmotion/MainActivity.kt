package com.example.composenavmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.composenavmotion.NavAnimation
import com.composenavmotion.animatedComposable
import com.example.composenavmotion.ui.checkout.CheckoutScreen
import com.example.composenavmotion.ui.detail.DetailScreen
import com.example.composenavmotion.ui.home.HomeScreen
import com.example.composenavmotion.ui.profile.ProfileScreen
import com.example.composenavmotion.ui.sheet.SheetScreen
import com.example.composenavmotion.ui.theme.ComposeNavMotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavMotionTheme {
                val navController = rememberNavController()
                val directionAwareAnimation = NavAnimation.directionAware(
                    forward = NavAnimation.slideLeft(),
                    backward = NavAnimation.slideRight(),
                )
                Scaffold { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        animatedComposable(route = "home", animation = directionAwareAnimation) {
                            HomeScreen(
                                onOpenDetail = { item ->
                                    navController.navigate("details/${item.id}")
                                },
                                onOpenSheet = { navController.navigate("sheet") },
                                onOpenProfile = { navController.navigate("profile") },
                                onOpenCheckoutFlow = { navController.navigate("checkout-details") },
                            )
                        }
                        animatedComposable(
                            route = "checkout-details",
                            animation = directionAwareAnimation,
                        ) {
                            DetailScreen(
                                itemId = "checkout-flow",
                                onBack = { navController.popBackStack() },
                                onContinueToCheckout = { navController.navigate("checkout") },
                            )
                        }
                        animatedComposable(
                            route = "checkout",
                            animation = directionAwareAnimation,
                        ) {
                            CheckoutScreen(onBack = { navController.popBackStack() })
                        }
                        animatedComposable(
                            route = "details/{itemId}",
                            animation = NavAnimation.slideLeft(),
                            arguments = listOf(
                                navArgument("itemId") { type = NavType.StringType },
                            ),
                        ) { entry ->
                            DetailScreen(
                                itemId = entry.arguments?.getString("itemId").orEmpty(),
                                onBack = { navController.popBackStack() },
                            )
                        }
                        animatedComposable(
                            route = "profile",
                            animation = NavAnimation.custom(
                                enter = { slideInHorizontally(animationSpec = tweenSpec()) },
                                exit = { slideOutHorizontally(animationSpec = tweenSpec()) },
                            ),
                        ) {
                            ProfileScreen(onBack = { navController.popBackStack() })
                        }
                        animatedComposable(
                            route = "sheet",
                            animation = NavAnimation.custom(
                                enter = { slideInVertically(animationSpec = tweenSpec()) },
                                exit = { fadeOut(animationSpec = tweenSpec()) },
                                popEnter = { fadeIn(animationSpec = tweenSpec()) },
                                popExit = { slideOutVertically(animationSpec = tweenSpec()) },
                                duration = 350,
                            ),
                        ) {
                            SheetScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
