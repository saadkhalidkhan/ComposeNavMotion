package com.example.composenavmotion.ui.detail

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
import com.example.composenavmotion.ui.home.sampleHomeItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    itemId: String,
    onBack: () -> Unit,
    onContinueToCheckout: (() -> Unit)? = null,
    animationDescription: String? = null,
) {
    val item = sampleHomeItems.firstOrNull { it.id == itemId }
    val title = item?.title ?: "Details"

    Scaffold(
        topBar = {
            SampleTopBar(title = title, onBack = onBack)
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
            Text(text = title, style = MaterialTheme.typography.headlineSmall)
            Text(
                text = item?.subtitle ?: "Transition preview",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(top = 12.dp),
            )
            Text(
                text = animationDescription ?: if (onContinueToCheckout != null) {
                    "Forward navigation uses slideLeft"
                } else {
                    "Opened with slideLeft preset"
                },
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
            )
            onContinueToCheckout?.let { onContinue ->
                Button(
                    onClick = onContinue,
                    modifier = Modifier.padding(top = 24.dp),
                ) {
                    Text("Continue to Checkout")
                }
            }
        }
    }
}
