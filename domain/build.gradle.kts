plugins {
    id("kotlin")
    id("idea")
    id("com.adarshr.test-logger")
}

sourceSets.all {
    java.srcDir("build/generated/ksp/main/kotlin")
}

dependencies {
    // KOTLIN
    implementation(libs.kotlin.stdlib)
    implementation(libs.kotlinx.coroutines.core)
    // KOIN
    implementation(libs.koin.core)
    implementation(libs.koin.annotations)
    ksp(libs.koin.compiler)

    // TEST
    testImplementation(libs.junit)
    testImplementation(libs.mockito.inline)
    testImplementation(libs.mockito.kotlin)
    implementation(libs.kotlinx.coroutines.test)
}
