// File: build.gradle.kts (Project-level)
plugins {
    // AGP for Android applications - using a stable 8.x version
    id("com.android.application") version "8.2.2" apply false
    // Kotlin Android plugin - matching Kotlin version 1.9.x for Compose compatibility
    id("org.jetbrains.kotlin.android") version "1.9.25" apply false
    // (No Firebase or Google services plugins)
}

// No additional buildscript classpath needed because plugins are declared above.
// We also rely on the Gradle settings.gradle.kts to include Google and MavenCentral repositories.
