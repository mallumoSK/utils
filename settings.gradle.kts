pluginManagement {
    repositories {
        google()
        gradlePluginPortal()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://repo.repsy.io/mvn/mallumo/public")
    }

    infix fun PluginDependencySpec.versionX(key: String): PluginDependencySpec = version(extra[key] as String)

    plugins {
        kotlin("multiplatform") versionX "version.kotlin" apply false
        kotlin("jvm") versionX "version.kotlin" apply false
        kotlin("android") versionX "version.kotlin" apply false
        id("com.android.application") versionX "version.agp" apply false
        id("com.android.library") versionX "version.agp" apply false
    }
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
        maven("https://repo.repsy.io/mvn/mallumo/public")
    }
}

rootProject.name = "utils"
include("test:desktop")
include(":utils")
