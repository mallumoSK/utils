@file:OptIn(ExperimentalWasmDsl::class)

import org.jetbrains.kotlin.gradle.*
import org.jetbrains.kotlin.gradle.dsl.*
import java.util.*

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.android.library)
    id("maven-publish")
}

group = libs.me.utils.get().group
version = libs.versions.me.utils.get()

kotlin {

    jvmToolchain(17)

    js(IR) {
        browser()
    }

    wasmJs {
        browser()
    }

    jvm()

    android {
        namespace = libs.me.utils.get().let { "${group}.${name}" }
        compileSdk = libs.versions.android.sdk.compile.get().toInt()
        minSdk = libs.versions.android.sdk.min.get().toInt()
    }
    applyDefaultHierarchyTemplate()
    sourceSets {
        commonMain.dependencies {
            implementation(libs.kotlin.datetime)
            implementation(libs.kotlin.coroutines)
            implementation(libs.compose.runtime)
            implementation(libs.kotlin.serialization)
        }


        val commonJavaMain by creating {
            dependsOn(commonMain.get())
        }

        jvmMain {
            dependsOn(commonJavaMain)
        }

        androidMain {
            dependsOn(commonJavaMain)
            dependencies {
                implementation(libs.androidx.core)
            }
        }
    }
    compilerOptions {
        optIn.add("kotlin.time.ExperimentalTime")
    }
}

publishing {
    val rName = propertiesLocal["repsy.name"]
    val rKey = propertiesLocal["repsy.key"]
    repositories {
        mavenLocal()
        maven {
            name = "repsy.io"
            url = uri("https://repo.repsy.io/mvn/${rName}/public")
            credentials {
                username = rName
                password = rKey
            }
        }
    }
}


val Project.propertiesLocal: LocalProperties get() = LocalProperties.get(this)

class LocalProperties private constructor(private val project: Project) {
    val prop = Properties().apply {
        project.rootProject.file("local.properties").reader().use {
            load(it)
        }
    }

    companion object {
        private lateinit var instance: LocalProperties
        internal fun get(project: Project): LocalProperties {
            if (!::instance.isInitialized) {
                instance = LocalProperties(project)
            }
            return instance
        }
    }

    operator fun get(key: String): String? = prop[key] as? String
}
