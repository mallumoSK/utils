@file:Suppress("unused")

package tk.mallumo.utils

import kotlinx.datetime.*


object GenId {

    private var wrapper = Clock.System.now().toEpochMilliseconds()

    val last: Long get() = wrapper

    fun get(): Long = wrapper++

    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): Long = get()
}
