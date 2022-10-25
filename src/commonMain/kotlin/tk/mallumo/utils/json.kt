@file:Suppress("unused")

package tk.mallumo.utils


import com.google.gson.Gson
import com.google.gson.GsonBuilder


fun Any?.toJson(prettyPrint: Boolean = false): String =
    GsonBuilder().apply {
        if (prettyPrint)
            setPrettyPrinting()
    }.create().toJson(this)

inline fun <reified T> String.fromJson(): T = Gson().fromJson(this, T::class.java)

