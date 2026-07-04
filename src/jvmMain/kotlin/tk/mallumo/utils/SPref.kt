package tk.mallumo.utils

import java.io.*
import kotlin.reflect.*
import kotlinx.serialization.json.*

actual class SPref(path: String) : SPrefConnectorImpl {

    private val data = mutableMapOf<String, JsonElement>()

    private val file = File(path).apply {
        if (!exists()) {
            createNewFile()
            writeText("{}")
        } else {
            try {
                val element = Json.parseToJsonElement(readText())
                if (element is JsonObject) {
                    data.putAll(element)
                }
            } catch (e: Exception) {
                // handle or ignore
            }
        }
    }

    private val holder = SPrefImpl(this)

    companion object {

        private lateinit var sPref: SPref

        operator fun get(path: String): SPref {
            synchronized(SPref::class) {
                if (!::sPref.isInitialized) {
                    sPref = SPref(path)
                }
                return sPref
            }
        }
    }

    actual operator fun <T : Any> get(key: Enum<*>, _default: T): T = holder.get(key, _default)

    actual operator fun <T : Any> get(key: String, _default: T): T = holder.get(key, _default)

    actual inline operator fun <reified T : Any> set(key: String, value: T) = set(key, value, T::class)

    actual inline operator fun <reified T : Any> set(key: Enum<*>, value: T) = set(key.name, value, T::class)

    actual fun <T : Any> set(key: String, value: T, clazz: KClass<T>) = holder.set(key, value, clazz)

    private fun persist() {
        val jsonObject = JsonObject(data)
        file.writeText(jsonObject.toString())
    }

    //PUT

    override fun putInt(key: String, value: Int) {
        data[key] = JsonPrimitive(value)
        persist()
    }

    override fun putString(key: String, value: String) {
        data[key] = JsonPrimitive(value)
        persist()
    }

    override fun putLong(key: String, value: Long) {
        data[key] = JsonPrimitive(value)
        persist()
    }

    override fun putBoolean(key: String, value: Boolean) {
        data[key] = JsonPrimitive(value)
        persist()
    }

    override fun putFloat(key: String, value: Float) {
        data[key] = JsonPrimitive(value)
        persist()
    }

    //GET

    override fun getInt(key: String): Int? =
        data[key]?.jsonPrimitive?.intOrNull

    override fun getString(key: String): String? =
        data[key]?.jsonPrimitive?.contentOrNull

    override fun getLong(key: String): Long? =
        data[key]?.jsonPrimitive?.longOrNull

    override fun getBoolean(key: String): Boolean? =
        data[key]?.jsonPrimitive?.booleanOrNull

    override fun getFloat(key: String): Float? =
        data[key]?.jsonPrimitive?.floatOrNull
}
