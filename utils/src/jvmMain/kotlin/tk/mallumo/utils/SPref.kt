package tk.mallumo.utils

import com.google.gson.*
import java.io.*
import kotlin.reflect.*

actual class SPref(path: String) : SPrefConnectorImpl {

    private var virtualData: JsonObject

    private val file = File(path).apply {
        virtualData = if (!exists()) {
            createNewFile()
            writeText("{}")
            JsonObject()
        } else {
            JsonParser.parseString(readText()).asJsonObject
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

    //PUT

    override fun putInt(key: String, value: Int) {
        virtualData.addProperty(key, value)
        file.writeText(virtualData.toString())
    }

    override fun putString(key: String, value: String) {
        virtualData.addProperty(key, value)
        file.writeText(virtualData.toString())
    }

    override fun putLong(key: String, value: Long) {
        virtualData.addProperty(key, value)
        file.writeText(virtualData.toString())
    }

    override fun putBoolean(key: String, value: Boolean) {
        virtualData.addProperty(key, value)
        file.writeText(virtualData.toString())
    }

    override fun putFloat(key: String, value: Float) {
        virtualData.addProperty(key, value)
        file.writeText(virtualData.toString())
    }

    //GET

    override fun getInt(key: String): Int? =
        virtualData.takeIf { it.has(key) }
            ?.let { it[key].asInt }

    override fun getString(key: String): String? =
        virtualData.takeIf { it.has(key) }
            ?.let { it[key].asString }

    override fun getLong(key: String): Long? =
        virtualData.takeIf { it.has(key) }
            ?.let { it[key].asLong }

    override fun getBoolean(key: String): Boolean? =
        virtualData.takeIf { it.has(key) }
            ?.let { it[key].asBoolean }

    override fun getFloat(key: String): Float? =
        virtualData.takeIf { it.has(key) }
            ?.let { it[key].asFloat }
}
