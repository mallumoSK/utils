@file:Suppress("unused")

package tk.mallumo.utils

import kotlin.reflect.*
import kotlinx.serialization.json.*
import kotlinx.serialization.*

@PublishedApi
internal val jsonInline: Json by lazy {
    Json { }
}

private val jsonPretty: Json by lazy {
    Json {
        prettyPrint = true
    }
}

@Suppress("UNCHECKED_CAST")
fun Any?.toJson(prettyPrint: Boolean = false): String {
    if (this == null) return "null"
    val serializer = serializer(this::class.java)
    return if (prettyPrint) jsonPretty.encodeToString(serializer, this)
    else jsonInline.encodeToString(serializer, this)
}

inline fun <reified T : Any> String.fromJson(): T = jsonInline.decodeFromString(this)

@Suppress("UNCHECKED_CAST")
fun <T : Any> String.parse(clazz: KClass<T>): T {
    val serializer = serializer(clazz.java)
    return jsonInline.decodeFromString(serializer, this) as T
}
