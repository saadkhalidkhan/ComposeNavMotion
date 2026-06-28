package com.example.composenavmotion.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.composenavmotion.ui.components.SampleTopBar
import com.example.composenavmotion.ui.home.sampleHomeItems

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    onBack: () -> Unit,
    onOpenDetail: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            SampleTopBar(title = "Item list", onBack = onBack)
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
                        .clickable { onOpenDetail(item.id) },
                    headlineContent = { Text(item.title) },
                    supportingContent = { Text(item.subtitle) },
                )
            }
        }
    }
}
