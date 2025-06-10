package com.example.quilltales
//Main Page - Controls everything, decides which screen to be shown at which phase
import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import com.example.quilltales.ui.theme.QuillTalesTheme         //Importing the master theme file


//sealed class restricts which all class can inherit from it
sealed class Screen {
    object AuthChoice : Screen()
    object Registration : Screen()              /*screens*/
    object Login : Screen()
    object Onboarding : Screen()
    object TempWelcome : Screen()
    object MainMenu : Screen()
    data class StoryDetail(
        val title: String,
        val content: String,
        val imageRes: Int? = null    // ← carry an optional drawable ID
    ) : Screen()
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("quilltales_prefs", Context.MODE_PRIVATE)

        val regUsername = prefs.getString("reg_username", "") ?: ""
        val regPassword = prefs.getString("reg_password", "") ?: ""
        val storedName  = prefs.getString("name", "") ?: ""
        val storedAge   = prefs.getInt("age", 0)
        val isLoggedIn  = prefs.getBoolean("is_logged_in", false)

        // Decide first screen
        val initialScreen = when {
            regUsername.isEmpty() || regPassword.isEmpty() -> Screen.AuthChoice
            storedName.isEmpty() || storedAge == 0         -> Screen.Login
            !isLoggedIn                                    -> Screen.Login
            else                                           -> Screen.MainMenu
        }

        setContent {
            var isDarkTheme by remember { mutableStateOf(false) } //mutablestateof -function of jetpack-updates the UI automatically

            QuillTalesTheme(darkTheme = isDarkTheme) {
                var currentScreen by remember { mutableStateOf<Screen>(initialScreen) }
                var userName      by remember { mutableStateOf(storedName) }
                var userAge       by remember { mutableStateOf(storedAge) }

                when (val screen = currentScreen) {
                    /* ───────── AuthChoice ───────── */
                    is Screen.AuthChoice -> AuthChoiceScreen(
                        onRegisterClicked = { currentScreen = Screen.Registration },
                        onLoginClicked    = { currentScreen = Screen.Login }
                    )

                    /* ───────── Registration ─────── */
                    is Screen.Registration -> RegistrationScreen { username, password ->
                        with(prefs.edit()) {
                            putString("reg_username", username.trim())
                            putString("reg_password", password.trim())
                            apply()
                        }
                        currentScreen = Screen.Onboarding
                    }

                    /* ───────── Login ────────────── */
                    is Screen.Login -> LoginScreen(onLoginSuccess = {
                        prefs.edit().putBoolean("is_logged_in", true).apply()
                        currentScreen = if (prefs.getString("name", "")!!.isNotEmpty() &&
                            prefs.getInt("age", 0) != 0)
                            Screen.MainMenu else Screen.Onboarding
                    })

                    /* ───────── Onboarding ───────── */
                    is Screen.Onboarding -> OnboardingScreen { name, age ->
                        userName = name
                        userAge  = age
                        with(prefs.edit()) {
                            putString("name", name)
                            putInt("age", age)
                            putBoolean("is_logged_in", true)
                            apply()
                        }
                        currentScreen = Screen.TempWelcome
                    }

                    /* ───────── TempWelcome ─────── */
                    is Screen.TempWelcome ->
                        TempWelcomeScreen(userName) { currentScreen = Screen.MainMenu }

                    /* ───────── MainMenu ─────────── */
                    is Screen.MainMenu -> MainMenuScreen(
                        userName      = userName,
                        userAge       = userAge,
                        isDarkTheme   = isDarkTheme,
                        onThemeChange = { isDarkTheme = it },
                        onStoryClick  = { title, content ->
                            // Pick image per story title
                            val imgRes = when (title) {
                                "The Lord of the Rings" -> R.drawable.lotr
                                "The Hobbit"            -> R.drawable.hobbit
                                "Underworld"             -> R.drawable.underworld
                                "Veil of the Silver Swan"   -> R.drawable.serene
                                "The Happy Prince"          -> R.drawable.prince
                                "Heart of the Fallen Star"   -> R.drawable.stardust
                                else                    -> null
                            }
                            currentScreen = Screen.StoryDetail(title, content, imgRes)
                        },
                        onLogout = {
                            prefs.edit().putBoolean("is_logged_in", false).apply()
                            currentScreen = Screen.Login
                        },
                        onDeleteAccount = {
                            prefs.edit().clear().apply()
                            currentScreen = Screen.Registration
                        }
                    )

                    /* ───────── StoryDetail ─────── */
                    is Screen.StoryDetail -> StoryDetailScreen(
                        storyTitle   = screen.title,
                        storyContent = screen.content,
                        imageRes     = screen.imageRes,      // ← pass through the drawable ID
                        onBack       = { currentScreen = Screen.MainMenu }
                    )
                }
            }
        }
    }
}
