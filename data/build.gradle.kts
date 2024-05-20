plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    id("kotlinx-serialization")
    id("com.adarshr.test-logger")
}

android {
    namespace = "com.mikhaellopez.data"
    libraryVariants.all {
        // KSP - To use generated sources
        addJavaSourceFoldersToModel(file("build/generated/ksp/$name/kotlin"))
    }
}

dependencies {
    // MODULE
    implementation(project(":domain"))

    // KOTLIN
    implementation(libs.kotlin.stdlib)
    // ANDROIDX
    implementation(libs.androidx.core.ktx)
    // COMPONENTS
    implementation(libs.room)
    ksp(libs.room.compiler)
    // KOIN
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.koin.annotations)
    ksp(libs.koin.compiler)
    // WEBSERVICES
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.okhttp)
    implementation(libs.ktor.serialization.json)
    implementation(libs.ktor.content.negotiation)

    // DEBUG TOOLS
    implementation(libs.ktor.client.logging)
    debugApi(libs.chucker.debug)
    releaseApi(libs.chucker.release)

    // TEST
    testImplementation(libs.junit)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    implementation(libs.kotlinx.coroutines.test)

    // ANDROID TEST
    androidTestImplementation(libs.androidx.test.runner)
    androidTestImplementation(libs.androidx.test.junit)
    androidTestImplementation(libs.kotlinx.coroutines.test)
}
