package com.example.composenavmotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.composenavmotion.NavAnimation
import com.composenavmotion.animatedComposable
import com.example.composenavmotion.ui.theme.ComposeNavMotionTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavMotionTheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        animatedComposable(route = "home", animation = NavAnimation.fade()) {
                            HomeScreen(onNavigateToDetails = { navController.navigate("details") })
                        }
                        animatedComposable(route = "details", animation = NavAnimation.slideLeft()) {
                            DetailsScreen(onBack = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen(onNavigateToDetails: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Home Screen", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onNavigateToDetails, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Go to Details")
        }
    }
}

@Composable
fun DetailsScreen(onBack: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(text = "Details Screen", style = MaterialTheme.typography.headlineMedium)
        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Go Back")
        }
    }
}
