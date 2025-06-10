package com.example.quilltales
//First interface of the App-includes the register and login button
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun AuthChoiceScreen(
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit
) {
    // ── Background ──
    val oceanGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFFEFF7F6), // soft sea foam
            Color(0xFFFBEBD7)  // light sandy beach
        )
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(oceanGradient)     //to fill the gradient to fulll screen
            .padding(32.dp)
    ) {
        Column(
            modifier = Modifier.align(Alignment.Center),
            verticalArrangement = Arrangement.Center,                      //to set everything on center
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // App logo at the top
            Image(
                painter           = painterResource(R.drawable.applogo),
                contentDescription= "App Logo",
                modifier          = Modifier                             // Adding App logo in the first screen
                    .size(200.dp)
                    .padding(bottom = 24.dp),
                contentScale      = ContentScale.Fit
            )

            Text(
                text = "Welcome to QuillTales!",
                style = MaterialTheme.typography.displayLarge
            )
            Spacer(modifier = Modifier.height(32.dp))
            Button(
                onClick = onRegisterClicked,
                modifier = Modifier.fillMaxWidth()             /*register and login buttons on first screen*/
            ) {
                Text("Register")
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onLoginClicked,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Login")
            }
        }
    }
}
