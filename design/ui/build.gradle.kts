plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.mikhaellopez.ui"
    buildFeatures {
        compose = true
    }
}

dependencies {
    // MODULE
    implementation(project(":domain"))

    // KOTLIN
    implementation(libs.kotlin.stdlib)
    // ANDROIDX
    implementation(libs.androidx.appcompat)
    // COMPOSE
    api(platform(libs.compose.bom))
    api(libs.compose.ui)
    debugApi(libs.compose.ui.tooling)
    debugApi(libs.customview.poolingcontainer)
    api(libs.compose.ui.tooling.preview)
    api(libs.compose.foundation)
    api(libs.compose.material)
    api(libs.compose.material.icons.ext)
    api(libs.activity.compose)
    implementation(libs.accompanist.swiperefresh)
    implementation(libs.coil.compose)
    // NAVIGATION
    api(libs.navigation.fragment)
    api(libs.navigation.ui)
}
