package com.example.quilltales.ui.theme

import com.example.quilltales.R
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.unit.sp

private val LightColorPalette = lightColorScheme(
    primary              = Color(0xFF00796B), // Teal 700
    onPrimary            = Color.White,
    primaryContainer     = Color(0xFFB2DFDB), // Teal 100
    onPrimaryContainer   = Color(0xFF004D40),

    secondary            = Color(0xFF4DD0E1), // Ocean Blue 200
    onSecondary          = Color.Black,
    secondaryContainer   = Color(0xFFE0F7FA), // Ocean Blue 50
    onSecondaryContainer = Color(0xFF006064),

    tertiary             = Color(0xFF80CBC4), // Teal 200
    onTertiary           = Color.Black,
    tertiaryContainer    = Color(0xFFE0F2F1), // Teal 50
    onTertiaryContainer  = Color(0xFF004D40),

    background           = Color(0xFFFFFFFF),
    onBackground         = Color.Black,

    surface              = Color(0xFFFFFFFF),
    onSurface            = Color.Black,

    surfaceVariant       = Color(0xFFF1F1F1), // light neutral for drawers/cards
    onSurfaceVariant     = Color(0xFF333333),

    outline              = Color(0xFF90A4AE)  // subtle grey
)

// ─── Dark theme: Ocean 200 → Teal 200 → Teal 100 ────────────────────────────
private val DarkColorPalette = darkColorScheme(
    primary              = Color(0xFF4DD0E1), // Ocean Blue 200
    onPrimary            = Color.Black,
    primaryContainer     = Color(0xFF26C6DA), // Ocean Blue 300
    onPrimaryContainer   = Color.Black,

    secondary            = Color(0xFF80CBC4), // Teal 200
    onSecondary          = Color.Black,
    secondaryContainer   = Color(0xFF4DB6AC), // Teal 300
    onSecondaryContainer = Color.Black,

    tertiary             = Color(0xFFB2DFDB), // Teal 100
    onTertiary           = Color.Black,
    tertiaryContainer    = Color(0xFF80CBC4), // Teal 200
    onTertiaryContainer  = Color.Black,

    background           = Color(0xFF121212),
    onBackground         = Color.White,

    surface              = Color(0xFF121212),
    onSurface            = Color.White,

    surfaceVariant       = Color(0xFF2C2C2C), // dark neutral for drawers/cards
    onSurfaceVariant     = Color.White,

    outline              = Color(0xFF546E7A)  // deeper grey
)

@Composable
fun QuillTalesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colorScheme = colors,
        typography  = AppTypography,
        content     = content
    )
}

// ─── Font families ────────────────────────────────────────────────────────────
val StyleScript    = FontFamily(Font(R.font.style_script_regular, FontWeight.Normal))
val OreganoRegular = FontFamily(Font(R.font.oregano_regular,       FontWeight.Normal))
val OreganoItalic  = FontFamily(Font(R.font.oregano_italic,        FontWeight.Normal))
val LatoBlack      = FontFamily(Font(R.font.lato_black,            FontWeight.Black))
val LatoRegular    = FontFamily(Font(R.font.lato_regular,          FontWeight.Normal))
val MarkoOne       = FontFamily(Font(R.font.markoone_regular,      FontWeight.Normal))

// ─── Start from the default Material3 typography, override only what you need ─
private val DefaultTypography = Typography()

val AppTypography = DefaultTypography.copy(
    // “QuillTales” app title
    displayLarge = TextStyle(
        fontFamily = StyleScript,
        fontWeight  = FontWeight.Bold,
        fontSize    = 38.sp
    ),

    // “Menu” header & buttons (“Register”, “Login”)
    headlineLarge = TextStyle(
        fontFamily = LatoBlack,
        fontWeight  = FontWeight.Normal,
        fontSize    = 20.sp
    ),

    // Drawer options
    labelMedium = TextStyle(
        fontFamily = LatoRegular,
        fontWeight  = FontWeight.Normal,
        fontSize    = 16.sp
    ),

    // Story titles
    titleLarge = TextStyle(
        fontFamily = MarkoOne,
        fontWeight  = FontWeight.Bold,
        fontSize    = 24.sp
    ),

    // Story content
    bodyMedium = TextStyle(
        fontFamily = MarkoOne,
        fontWeight  = FontWeight.Normal,
        fontSize    = 16.sp
    ),

    // Greetings & subtitles :
    headlineMedium = TextStyle(
        fontFamily = OreganoRegular,
        fontWeight  = FontWeight.Normal,
        fontSize    = 28.sp
    ),
    // Subtitles (TempWelcome subtitle & MainMenu “journey…”)
    headlineSmall = TextStyle(
        fontFamily = OreganoItalic,
        fontWeight  = FontWeight.Normal,
        fontSize    = 20.sp
    )
)
