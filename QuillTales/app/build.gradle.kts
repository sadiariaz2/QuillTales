// File: app/build.gradle.kts (Module-level)
plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.quilltales"         // app's package name
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.quilltales"
        minSdk = 21                              // minimum Android version supported
        targetSdk = 35                           // target the latest Android version
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        // Target Java 17 for modern language features (compatible with AGP 8+)
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true                           // Enable Jetpack Compose
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15" // Match Compose Compiler to Kotlin 1.9.25&#8203;:contentReference[oaicite:6]{index=6}
    }
}

dependencies {
    // Core Android Kotlin extensions
    implementation("androidx.core:core-ktx:1.15.0")
    // Jetpack Compose UI libraries (using Material3 for modern design)
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.ui:ui:1.7.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.7.8")
    implementation("androidx.compose.material3:material3:1.3.1")

}
