package com.example.composenavmotion.ui.nested

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenavmotion.ui.components.SampleTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedHomeScreen(
    onBack: () -> Unit,
    onOpenDetails: () -> Unit,
) {
    Scaffold(
        topBar = {
            SampleTopBar(title = "Nested graph", onBack = onBack)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "This destination lives inside animatedNavigation<MainGraph>.",
                style = MaterialTheme.typography.bodyLarge,
            )
            Button(
                onClick = onOpenDetails,
                modifier = Modifier.padding(top = 24.dp),
            ) {
                Text("Open nested details")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NestedDetailsScreen(
    id: String,
    onBack: () -> Unit,
) {
    Scaffold(
        topBar = {
            SampleTopBar(title = "Nested details", onBack = onBack)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "Nested item: $id", style = MaterialTheme.typography.headlineSmall)
            Text(
                text = "Inherits graph-level sharedAxisY animation override.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}
