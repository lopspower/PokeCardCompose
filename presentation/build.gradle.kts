plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
    id("kotlinx-serialization")
    id("androidx.navigation.safeargs")
    id("com.adarshr.test-logger")
}

android {
    namespace = "com.mikhaellopez.presentation"
    defaultConfig {
        applicationId = "com.mikhaellopez.pokecardcompose"
        versionCode = 2
        versionName = "1.1.0"
    }
    signingConfigs {
        create("release") {
            storeFile = file("keystore")
            keyAlias = "alias"
            keyPassword = "password"
            storePassword = "password"
            enableV3Signing = true
        }
    }
    buildTypes {
        debug {
            applicationIdSuffix = ".debug"
            versionNameSuffix = "-DEBUG"
        }
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = true
            isShrinkResources = true
            val proguardFolder = "../proguard/"
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                proguardFolder + "kotlin-rules.pro",
                proguardFolder + "model-rules.pro",
                proguardFolder + "log-rules.pro",
                proguardFolder + "okhttp3-rules.pro",
                proguardFolder + "ktor-logging-rules.pro"
            )
        }
    }
    applicationVariants.all {
        // KSP - To use generated sources
        addJavaSourceFoldersToModel(file("build/generated/ksp/$name/kotlin"))
    }
}

dependencies {
    // MODULE
    implementation(project(":data"))
    implementation(project(":domain"))
    implementation(project(":design:ui"))

    // KOTLIN
    implementation(libs.kotlin.stdlib)
    // ANDROIDX
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.preference)
    implementation(libs.multidex)
    // KOIN
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    implementation(libs.koin.compose)
    ksp(libs.koin.compiler)
    // NAVIGATION
    implementation(libs.navigation.compose)

    // TEST
    testImplementation(libs.junit)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    implementation(libs.kotlinx.coroutines.test)

    // ANDROID TEST
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.compose.ui.test.junit)
    debugImplementation(libs.compose.ui.test.manifest)
}
