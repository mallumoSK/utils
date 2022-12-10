import android.annotation.*

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
//        publishLibraryVariants("release", "debug")
        publishLibraryVariants("release")
        publishLibraryVariantsGroupedByFlavor = true
    }
    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }

    }

    sourceSets {
        @Suppress("UNUSED_VARIABLE") val commonMain by getting {
            dependencies {
                implementation("com.google.code.gson:gson:$version_gson")
            }
        }

        @Suppress("UNUSED_VARIABLE") val jvmMain by getting {

        }
        @Suppress("UNUSED_VARIABLE") val androidMain by getting {
            dependencies {
                implementation("androidx.core:core:1.9.0")
            }

        }

    }
}

android {
    compileSdk = 31
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")

    defaultConfig {
//        minSdk = 24
//        targetSdk = 33


        minSdkVersion(21)
        targetSdkVersion(31)
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    lintOptions.isAbortOnError = false
    lintOptions.isCheckReleaseBuilds = false
    lintOptions.disable("TypographyFractions", "TypographyQuotes")
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(8))
}


apply("../secure.gradle")
