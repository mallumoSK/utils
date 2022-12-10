package tk.mallumo.utils

import kotlin.reflect.*

internal interface SPrefConnectorImpl {
    fun putInt(key: String, value: Int)
    fun putString(key: String, value: String)
    fun putLong(key: String, value: Long)
    fun putBoolean(key: String, value: Boolean)
    fun putFloat(key: String, value: Float)

    fun getInt(key: String): Int?
    fun getString(key: String): String?
    fun getLong(key: String): Long?
    fun getBoolean(key: String): Boolean?
    fun getFloat(key: String): Float?
}

internal class SPrefImpl(val connector: SPrefConnectorImpl) {


    fun <T : Any> get(key: Enum<*>, _default: T): T = get(key.name, _default)

    @Suppress("UNCHECKED_CAST")
    fun <T : Any> get(key: String, _default: T): T {
        return when (_default::class) {
            Int::class -> connector.getInt(key) as? T ?: _default
            String::class -> connector.getString(key) as? T ?: _default
            Long::class -> connector.getLong(key) as? T ?: _default
            Boolean::class -> connector.getBoolean(key) as? T ?: _default
            Float::class -> connector.getFloat(key) as? T ?: _default
            else -> {
                throw RuntimeException("undefined sp type: ${_default::class.simpleName}")
            }
        }
    }

    fun <T : Any> set(key: String, value: T, clazz: KClass<T>) {
        when (clazz) {
            Int::class -> {
                connector.putInt(key, value as Int)
            }

            String::class -> {
                connector.putString(key, value as String)
            }

            Long::class -> {
                connector.putLong(key, value as Long)
            }

            Boolean::class -> {
                connector.putBoolean(key, value as Boolean)
            }

            Float::class -> {
                connector.putFloat(key, value as Float)
            }

            else -> {
                throw RuntimeException("undefined sp type: ${clazz.simpleName}")
            }
        }
    }
}
