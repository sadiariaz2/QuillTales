package com.example.quilltales
//Onboarding screen - for desired name field and Age based filter of stories
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp

@Composable
fun OnboardingScreen(onContinue: (String, Int) -> Unit) {
    var name by remember { mutableStateOf("") }
    var ageText by remember { mutableStateOf("") }
    var showError by remember { mutableStateOf(false) }
                            //setting constraints for age
    val isAgeValid = ageText.toIntOrNull()?.let { it in 7..45 } ?: false  //age limit
    val isNameValid = name.trim().length >= 2
    val canContinue = isAgeValid && isNameValid
  //Background
    val oceanGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEFF7F6), // soft sea foam
            Color(0xFFFDEFD3)  // soft sand
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(oceanGradient)
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to QuillTales!",
                style = MaterialTheme.typography.displayLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Please enter your name and age:",
                style = MaterialTheme.typography.labelMedium,
                fontStyle = FontStyle.Italic
            )
            Spacer(modifier = Modifier.height(24.dp))
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Your Name") },
                placeholder = { Text("Type your name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = ageText,
                onValueChange = {
                    if (it.length <= 2 && it.all { ch -> ch.isDigit() }) {
                        ageText = it       //two values for age
                        showError = false
                    }
                },
                label = { Text("Your Age") },
                placeholder = { Text("Enter your age") },
                isError = showError && !isAgeValid,
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            if (showError && !isAgeValid) {
                Text(
                    text = "Please enter a valid age between 7 and 45",
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.labelSmall
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    val age = ageText.toIntOrNull()
                    if (canContinue && age != null) {
                        onContinue(name.trim(), age)
                    } else {
                        showError = true
                    }
                },
                enabled = true,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continue")
            }
        }
    }
}
