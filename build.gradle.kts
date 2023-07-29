plugins {
    kotlin("multiplatform")
    id("com.android.library")
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
                implementation("androidx.core:core:1.9.0")
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
}


java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(11))
}
