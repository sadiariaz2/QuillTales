package com.example.quilltales
//This file manages how the story should be styled and set
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll    // for scrollable content
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale       // to control how the image scales
import androidx.compose.ui.res.painterResource     // to load drawable resources
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryDetailScreen(
    storyTitle: String,
    storyContent: String,
    imageRes: Int? = null,    // optional drawable ID for inline image
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(storyTitle) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())  // make content scrollable
        ) {
            // 1) Split content on [IMAGE] marker
            val parts = storyContent.split("[IMAGE]")

            // 2) Render each text part and inject image where the marker was
            parts.forEachIndexed { index, part ->
                // Render the text segment
                Text(
                    text = part.trim(),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(8.dp))

                // If not last part and imageRes provided, insert image
                if (index < parts.lastIndex && imageRes != null) {
                    Spacer(modifier = Modifier.height(16.dp))
                    Image(
                        painter = painterResource(id = imageRes),
                        contentDescription = null,             // decorative
                        modifier = Modifier
                            .fillMaxWidth()                   // span full width
                            .heightIn(min = 180.dp),          // ensure minimum height
                        contentScale = ContentScale.Crop       // crop to fill width
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }
}