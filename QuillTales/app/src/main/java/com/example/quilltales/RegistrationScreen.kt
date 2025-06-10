package com.example.quilltales
//Registration page
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun RegistrationScreen(onRegistrationSuccess: (String, String) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }    //mutablestateof -function of jetpack-updates the UI automatically
    var errorMessage by remember { mutableStateOf("") }
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
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "Register", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = username,
                onValueChange = { username = it },   //the fields for registration
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {        /*setting constraints for username and password*/
                    val usernameRegex = Regex("^[a-z0-9_.]+$") // only lowercase, numbers, _ and .
                    val passwordValid = password.length in 6..10 && !password.contains(" ")

                    when {
                        username.isBlank() || password.isBlank() -> {
                            errorMessage = "Please fill in all fields."
                        }
                        !usernameRegex.matches(username) -> {
                            errorMessage = "Username must be lowercase and can include letters, numbers, _ or ."
                        }
                        !passwordValid -> {
                            errorMessage = "Password must be 6–10 characters long with no spaces."
                        }
                        else -> {
                            errorMessage = ""
                            onRegistrationSuccess(username, password)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))        //error msg format
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
