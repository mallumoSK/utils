@file:Suppress("unused")

package tk.mallumo.utils

import java.util.concurrent.atomic.*

object GenID {

    private val wrapper = AtomicLong(System.currentTimeMillis())

    val last: Long get() = wrapper.get()

    fun get(): Long = wrapper.incrementAndGet()

    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): Long = get()
}
