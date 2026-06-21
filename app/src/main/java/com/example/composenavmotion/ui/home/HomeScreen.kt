package com.example.composenavmotion.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenavmotion.ui.components.SampleTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onOpenDetail: (HomeItem) -> Unit,
    onOpenSheet: () -> Unit,
    onOpenProfile: () -> Unit,
    onOpenCheckoutFlow: () -> Unit,
    onOpenMaterialRoute: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            SampleTopBar(
                title = "Home",
                navigationIcon = {
                    IconButton(onClick = onOpenProfile) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp),
        ) {
            items(sampleHomeItems, key = { it.id }) { item ->
                ListItem(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            when {
                                item.opensSheet -> onOpenSheet()
                                item.opensCheckoutFlow -> onOpenCheckoutFlow()
                                item.materialRoute != null -> onOpenMaterialRoute(item.materialRoute)
                                else -> onOpenDetail(item)
                            }
                        },
                    headlineContent = { Text(item.title) },
                    supportingContent = { Text(item.subtitle) },
                )
            }
        }
    }
}
