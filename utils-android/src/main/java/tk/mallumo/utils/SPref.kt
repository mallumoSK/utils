@file:Suppress("DEPRECATION")

package tk.mallumo.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager.getDefaultSharedPreferences
import kotlin.reflect.KClass


@Suppress("UNCHECKED_CAST")
class SPref private constructor(context: Context) {
    private val sp: SharedPreferences = getDefaultSharedPreferences(context)
    private val spValues = HashMap<String, Any>()


    companion object {
        private var sPref: SPref? = null

        fun getSP(context: Context): SPref {
            synchronized(SPref::class) {
                if (sPref == null) {
                    sPref = SPref(context.applicationContext).apply {
                        sp.all.filterNot { it.key.isNullOrEmpty() }
                            .also {
                                spValues.putAll(it as Map<out String, Any>)
                            }
                    }

                }
                return sPref!!
            }
        }
    }

    fun <T : Any> get(key: Enum<*>, _default: T): T = get(key.name, _default)

    fun <T : Any> get(key: String, _default: T): T = (spValues[key] as T?) ?: _default

    inline fun <reified T : Any> set(key: String, value: T) = set(key, value, T::class)

    inline fun <reified T : Any> set(key: Enum<*>, value: T) = set(key.name, value, T::class)

    fun <T : Any> set(key: String, value: T, clazz: KClass<T>) {
        spValues[key] = value as Any
        sp.edit().apply {
            when (clazz) {
                Int::class -> {
                    putInt(key, value as Int)
                }
                String::class -> {
                    putString(key, value as String)
                }
                Long::class -> {
                    putLong(key, value as Long)
                }
                Boolean::class -> {
                    putBoolean(key, value as Boolean)
                }
                Float::class -> {
                    putFloat(key, value as Float)
                }
                else -> {
                    throw RuntimeException("undefined sp type: ${clazz.simpleName}")
                }
            }
        }.apply()
    }
}