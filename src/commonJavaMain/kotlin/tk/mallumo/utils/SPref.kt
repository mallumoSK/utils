package tk.mallumo.utils

import kotlin.reflect.*

expect class SPref {

    operator fun <T : Any> get(key: Enum<*>, _default: T): T

    operator fun <T : Any> get(key: String, _default: T): T

    inline operator fun <reified T : Any> set(key: String, value: T)

    inline operator fun <reified T : Any> set(key: Enum<*>, value: T)

    fun <T : Any> set(key: String, value: T, clazz: KClass<T>)
}
