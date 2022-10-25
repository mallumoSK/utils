@file:Suppress("unused")

package tk.mallumo.utils

object GenID {
    @Suppress("MemberVisibilityCanBePrivate")
    var last = System.currentTimeMillis()
        private set

    fun get(): Long {
        last += 1
        return last
    }

    operator fun getValue(thisRef: Any?, property: kotlin.reflect.KProperty<*>): Long = get()
}
