pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral {
            mavenContent {
                releasesOnly()
            }
        }
    }
}

rootProject.name = "PokeCardCompose"
include(":presentation")
include(":domain")
include(":data")
include(":design:preview")
include(":design:ui")
