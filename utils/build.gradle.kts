import java.util.*

plugins {
    kotlin("multiplatform")
    id("com.android.library")
    id("maven-publish")
}

val toolkit by lazy {
    Toolkit.get(extensions = extensions.extraProperties)
}

group = "tk.mallumo"
version = toolkit["version.utils"]

val version_gson by toolkit

kotlin {

    android {
        publishLibraryVariants("release")
        publishLibraryVariantsGroupedByFlavor = true
    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }

    }
    sourceSets {
        @Suppress("UNUSED_VARIABLE") val commonMain by getting {
            dependencies {
                implementation("com.google.code.gson:gson:$version_gson")
            }
        }

        @Suppress("UNUSED_VARIABLE") val jvmMain by getting
        @Suppress("UNUSED_VARIABLE") val androidMain by getting {
            dependencies {
                implementation("androidx.core:core:1.9.0")
            }
        }
    }
}

@Suppress("UnstableApiUsage", "OldTargetApi", "DEPRECATION")
android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
        compileSdk = 31
        minSdk = 21
        targetSdk = 31
        namespace = "tk.mallumo.utils"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    lintOptions {
        isCheckReleaseBuilds = false
        isAbortOnError = false
        disable("TypographyFractions", "TypographyQuotes")
    }
    lint {
        abortOnError = false
        checkReleaseBuilds = false
        disable += setOf("TypographyFractions", "TypographyQuotes")
    }
    buildFeatures {
        buildConfig = false
    }
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}


val prop = Properties().apply {
    project.rootProject.file("local.properties").reader().use {
        load(it)
    }
}

publishing {
    val rName = prop["repsy.name"] as String
    val rKey = prop["repsy.key"] as String
    repositories {
        maven {
            name = "repsy.io"
            url = uri("https://repo.repsy.io/mvn/${rName}/public")
            credentials {
                username = rName
                password = rKey
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = "tk.mallumo"
            artifactId = "utils"
            version = toolkit["version.utils"]
        }
    }
}
