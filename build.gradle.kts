buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath(libs.gradle.plugin)
        classpath(libs.kotlin.serialization.plugin)
        classpath(libs.navigation.safeargs.plugin)
        classpath(libs.test.logger.plugin)
    }
}

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.compose.compiler) apply false
    alias(libs.plugins.kotlin.kapt) apply false
    alias(libs.plugins.ksp) apply false
    alias(libs.plugins.ktlint)
}

subprojects {
    apply(plugin = rootProject.libs.plugins.ksp.get().pluginId)
    apply(plugin = rootProject.libs.plugins.ktlint.get().pluginId)

    android {
        compileSdk = libs.versions.targetSdk.get().toInt()
        defaultConfig {
            minSdk = libs.versions.minSdk.get().toInt()
            vectorDrawables.useSupportLibrary = true
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
        buildFeatures {
            buildConfig = true
        }
        compileOptions {
            sourceCompatibility = JavaVersion.VERSION_17
            targetCompatibility = JavaVersion.VERSION_17
        }
    }
    ktlint {
        verbose.set(true)
        android.set(true)
        filter {
            exclude { projectDir.toURI().relativize(it.file.toURI()).path.contains("/generated/") }
        }
    }

    // Configurations for `com.android.application` plugin
    plugins.withType<com.android.build.gradle.AppPlugin>().configureEach {
        extensions.configure<com.android.build.api.dsl.ApplicationExtension> {
            defaultConfig {
                targetSdk = libs.versions.targetSdk.get().toInt()
            }
        }
    }

    // Configurations for `com.android.library` plugin
    plugins.withType<com.android.build.gradle.LibraryPlugin>().configureEach {
        extensions.configure<com.android.build.api.dsl.LibraryExtension> {
            buildTypes {
                release {
                    isMinifyEnabled = true
                }
            }
        }
    }
}

private typealias AndroidExtension = com.android.build.api.dsl.CommonExtension<*, *, *, *, *, *>

private val Project.androidExtension: AndroidExtension
    get() = extensions.getByType(com.android.build.api.dsl.CommonExtension::class.java)

private fun Project.android(block: AndroidExtension.() -> Unit) {
    plugins.withType<com.android.build.gradle.BasePlugin>().configureEach {
        androidExtension.block()
    }
}
