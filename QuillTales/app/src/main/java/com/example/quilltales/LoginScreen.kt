package com.example.quilltales
//Login Screen
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import android.content.Context

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    val sharedPref = context.getSharedPreferences("quilltales_prefs", Context.MODE_PRIVATE)
    val savedUsername = sharedPref.getString("reg_username", "") ?: ""
    val savedPassword = sharedPref.getString("reg_password", "") ?: ""

    // Background gradient brush
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
            Text(text = "Login", style = MaterialTheme.typography.headlineLarge)
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") },
                modifier = Modifier.fillMaxWidth()
            )                                                           /*Fields of login*/
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    when {           //To Check if username and password exists
                        savedUsername.isEmpty() -> errorMessage = "User not found. Please register."
                        username.trim() != savedUsername || password.trim() != savedPassword ->
                            errorMessage = "Wrong credentials."
                        else -> onLoginSuccess()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
            if (errorMessage.isNotEmpty()) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
            }
        }
    }
}
