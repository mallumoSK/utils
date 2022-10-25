package tk.mallumo.utils

import android.content.*
import android.preference.*
import kotlin.reflect.*

actual class SPref(context: Context) : SPrefConnectorImpl {

    @Suppress("DEPRECATION")
    private val sp: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    private val holder = SPrefImpl(this)
    private val data = mutableMapOf<String, Any>()

    companion object {

        private lateinit var sPref: SPref

        operator fun get(context: Context): SPref {
            synchronized(SPref::class) {
                if (!::sPref.isInitialized) {
                    sPref = SPref(context.applicationContext).apply {
                        sp.all.filterNot { it.key.isNullOrEmpty() }
                            .also {
                                @Suppress("UNCHECKED_CAST")
                                data.putAll(it as Map<String, Any>)
                            }
                    }
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
        data[key] = value
        sp.edit().putInt(key, value).apply()
    }

    override fun putString(key: String, value: String) {
        data[key] = value
        sp.edit().putString(key, value).apply()
    }

    override fun putLong(key: String, value: Long) {
        data[key] = value
        sp.edit().putLong(key, value).apply()
    }

    override fun putBoolean(key: String, value: Boolean) {
        data[key] = value
        sp.edit().putBoolean(key, value).apply()
    }

    override fun putFloat(key: String, value: Float) {
        data[key] = value
        sp.edit().putFloat(key, value).apply()
    }

    //GET

    override fun getInt(key: String): Int? = data[key] as? Int

    override fun getString(key: String): String? = data[key] as? String

    override fun getLong(key: String): Long? = data[key] as? Long

    override fun getBoolean(key: String): Boolean? = data[key] as? Boolean

    override fun getFloat(key: String): Float? = data[key] as? Float
}
