@file:Suppress("unused")

package tk.mallumo.utils


inline fun tryPrint(function: () -> Unit) {
    try {
        function.invoke()
    } catch (e: Throwable) {
        e.printStackTrace()
    }
}

inline fun tryIgnore(function: () -> Unit) {
    try {
        function.invoke()
    } catch (e: Throwable) {
    }
}

inline fun tryCatch(function: () -> Unit, exception: (e: Throwable) -> Unit) {
    try {
        function.invoke()
    } catch (e: Throwable) {
        exception.invoke(e)
    }
}

inline fun <T : Any> tryCatch(function: () -> T, exception: (e: Throwable) -> T): T {
    return try {
        function.invoke()
    } catch (e: Throwable) {
        exception.invoke(e)
    }
}