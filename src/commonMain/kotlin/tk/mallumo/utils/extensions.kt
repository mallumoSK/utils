@file:Suppress("unused")

package tk.mallumo.utils


import java.io.File
import kotlin.properties.ObservableProperty
import kotlin.properties.ReadWriteProperty

fun <T> decode(_when: () -> Boolean, _then: () -> T, _else: () -> T): T =
    if (_when.invoke()) _then.invoke() else _else.invoke()

inline fun <T> T.applyIf(expression: (T) -> Boolean, body: T.() -> Unit): T {
    if (expression.invoke(this)) body.invoke(this)
    return this
}

inline fun <T> T.alsoIf(expression: (T) -> Boolean, body: (T) -> Unit): T {
    if (expression.invoke(this)) body.invoke(this)
    return this
}

inline fun ifAllOK(vararg expression: Boolean?, callback: () -> Unit) {
    if (expression.all { it != null && it == true }) {
        callback.invoke()
    }
}


fun <T> default(defaultVal: T): ReadWriteProperty<Any?, T> =
    object : ObservableProperty<T>(defaultVal) {}

fun <T> default(defaultVal: () -> T): ReadWriteProperty<Any?, T> =
    object : ObservableProperty<T>(defaultVal.invoke()) {}

val String.file: File
    get() = File(this)

