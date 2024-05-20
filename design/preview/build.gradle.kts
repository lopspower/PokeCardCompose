plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.mikhaellopez.designsystem"
    defaultConfig {
        applicationId = "com.mikhaellopez.pokecardcompose.designsystem"
        versionCode = 1
        versionName = "1.0"
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
}

dependencies {
    // MODULE
    implementation(project(":design:ui"))

    // KOTLIN
    implementation(libs.kotlin.stdlib)
    // ANDROIDX
    implementation(libs.androidx.appcompat)
}
