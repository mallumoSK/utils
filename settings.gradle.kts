pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://mallumo.jfrog.io/artifactory/gradle-dev-local")
    }
    plugins {
        kotlin("multiplatform").version(extra["version.kotlin"] as String)
        kotlin("jvm").version(extra["version.kotlin"] as String)
        kotlin("android").version(extra["version.kotlin"] as String)
        id("com.android.application").version(extra["version.agp"] as String)
        id("com.android.library").version(extra["version.agp"] as String)
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://mallumo.jfrog.io/artifactory/gradle-dev-local")
    }
}

rootProject.name = "utils"
include("test:desktop")
include(":utils")
