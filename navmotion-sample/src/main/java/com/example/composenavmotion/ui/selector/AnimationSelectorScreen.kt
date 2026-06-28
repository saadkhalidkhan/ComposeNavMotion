package com.example.composenavmotion.ui.selector

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenavmotion.navigation.DemoAnimation
import com.example.composenavmotion.ui.components.SampleTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimationSelectorScreen(
    selected: DemoAnimation,
    onBack: () -> Unit,
    onSelect: (DemoAnimation) -> Unit,
) {
    Scaffold(
        topBar = {
            SampleTopBar(title = "Animation selector", onBack = onBack)
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            item {
                Text(
                    text = "Selected: ${selected.label}",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 12.dp),
                )
            }
            items(DemoAnimation.entries) { animation ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onSelect(animation) },
                    headlineContent = {
                        Text(
                            text = animation.label,
                            color = if (animation == selected) {
                                MaterialTheme.colorScheme.primary
                            } else {
                                MaterialTheme.colorScheme.onSurface
                            },
                        )
                    },
                    supportingContent = {
                        Text("Tap to preview with this default animation")
                    },
                )
            }
        }
    }
}
