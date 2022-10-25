@file:Suppress("unused")

package tk.mallumo.utils

import com.google.gson.*

private val gsonInline: Gson by lazy {
    GsonBuilder().create()
}

private val gsonPretty: Gson by lazy {
    GsonBuilder().apply {
        setPrettyPrinting()
    }.create()
}

fun Any?.toJson(prettyPrint: Boolean = false): String =
    if (prettyPrint) gsonPretty.toJson(this)
    else gsonInline.toJson(this)

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)

