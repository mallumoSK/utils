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
}