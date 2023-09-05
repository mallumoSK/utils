plugins {
    kotlin("multiplatform") version Deps.kotlin
    id("com.android.library") version Deps.agp
    id("maven-publish")
}

group = Deps.lib.group
version = Deps.lib.version

kotlin {

    androidTarget {
        publishLibraryVariants("release")
        publishLibraryVariantsGroupedByFlavor = true
    }

    js(IR)

    jvm {
        compilations.all {
            kotlinOptions.jvmTarget = "11"
        }
    }
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation(Deps.datetime)
                implementation(Deps.coroutines)
                implementation(Deps.compose)
            }
        }

        val commonJavaMain by creating {
            dependsOn(commonMain)
            dependencies {
                implementation(Deps.gson)
            }
        }
        val jsMain by getting

        val jvmMain by getting {
            dependsOn(commonJavaMain)
        }
        val androidMain by getting {
            dependsOn(commonJavaMain)
            dependencies {
                implementation(Deps.androidx.core)
            }
        }
    }
}

@Suppress("UnstableApiUsage", "OldTargetApi")
android {
    sourceSets["main"].manifest.srcFile("src/androidMain/AndroidManifest.xml")
    defaultConfig {
        compileSdk = 31
        minSdk = 21
        namespace = "${Deps.lib.group}.${Deps.lib.artifact}"
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    @Suppress("DEPRECATION")
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

publishing {
    val rName = propertiesLocal["repsy.name"]
    val rKey = propertiesLocal["repsy.key"]
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
            groupId = Deps.lib.group
            artifactId = Deps.lib.artifact
            version = Deps.lib.version
        }
    }
}

repositories {
    google()
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://repo.repsy.io/mvn/mallumo/public")
    gradlePluginPortal()
}
java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}
