package com.example.composenavmotion.ui.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.composenavmotion.shared.SharedNavElement
import com.example.composenavmotion.ui.components.SampleTopBar

private val demoUsers = listOf(
    "alex" to "Alex Morgan",
    "sam" to "Sam Rivera",
    "jordan" to "Jordan Lee",
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedElementListScreen(
    onBack: () -> Unit,
    onOpenProfile: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            SampleTopBar(title = "Shared elements", onBack = onBack)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            Text(
                text = "Tap a profile to preview SharedNavElement transitions.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(vertical = 12.dp),
            )
            demoUsers.forEach { (userId, name) ->
                ListItem(
                    modifier = Modifier.clickable { onOpenProfile(userId) },
                    leadingContent = {
                        SharedNavElement(key = "profile-image-$userId") {
                            ProfileAvatar(userId = userId)
                        }
                    },
                    headlineContent = { Text(name) },
                    supportingContent = { Text("Shared key: profile-image-$userId") },
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedProfileDetailScreen(
    userId: String,
    onBack: () -> Unit,
) {
    val name = demoUsers.firstOrNull { it.first == userId }?.second ?: "Profile"

    Scaffold(
        topBar = {
            SampleTopBar(title = name, onBack = onBack)
        },
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            SharedNavElement(key = "profile-image-$userId") {
                ProfileAvatar(userId = userId, large = true)
            }
            Text(
                text = name,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(top = 24.dp),
            )
            Text(
                text = "Animated with Compose SharedTransitionLayout + SharedNavElement.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 8.dp),
            )
        }
    }
}

@Composable
private fun ProfileAvatar(
    userId: String,
    large: Boolean = false,
) {
    val size = if (large) 120.dp else 48.dp
    Box(
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile $userId",
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
    }
}
