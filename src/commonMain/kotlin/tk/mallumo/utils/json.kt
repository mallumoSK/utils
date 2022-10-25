@file:Suppress("unused")

package tk.mallumo.utils

import com.google.gson.*
import kotlin.reflect.*

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

inline fun <reified T : Any> String.fromJson(): T = parse(T::class)

fun <T : Any> String.parse(clazz: KClass<T>) = gsonInline.fromJson(this, clazz.java)

