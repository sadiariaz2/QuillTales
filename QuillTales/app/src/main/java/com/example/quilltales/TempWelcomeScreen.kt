package com.example.quilltales
//temporary Welcome screen once the user is registered
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun TempWelcomeScreen(userName: String, onTimeout: () -> Unit) {
    LaunchedEffect(userName) {
        delay(5000L)  // Message appear for around 5 sec
        onTimeout()
    }

    // background
    val oceanGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEFF7F6), // soft sea foam
            Color(0xFFFDEFD3)  // soft sand
        )
    )

    Box(      /*Welcome Box*/
        modifier = Modifier
            .fillMaxSize()
            .background(oceanGradient),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            tonalElevation = 4.dp,
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.primaryContainer,
            modifier = Modifier.padding(32.dp)
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Greetings, $userName!",
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Embark on your journey through Tales and Imagination.",
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}
