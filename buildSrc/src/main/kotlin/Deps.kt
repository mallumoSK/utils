@Suppress("SpellCheckingInspection", "ClassName")
object Deps {
    const val kotlin = "1.9.23"
    const val agp = "8.0.2"

    object lib {
        const val group = "tk.mallumo"
        const val version = "1.9.23-1.0.2"
        const val artifact = "utils"
    }

    object androidx {
        const val core = "androidx.core:core-ktx:1.12.0"
    }

    const val gson = "com.google.code.gson:gson:2.10.1"
    const val datetime = "org.jetbrains.kotlinx:kotlinx-datetime:0.5.0"
    const val coroutines = "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0"
    const val compose = "org.jetbrains.compose.runtime:runtime:1.6.1"
}
