@file:Suppress("unused")

package tk.mallumo.utils

import java.text.*
import java.util.*
import java.util.concurrent.*


fun Long.toDateString(): String = dateFormatter.format(Date(this))

fun String.toDateMillis(): Long {
    return try {
        dateFormatter.parse(this)!!.time
    } catch (e: Exception) {
        0
    }
}

val dateFormatter by lazy {
    SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSXXX", Locale.ENGLISH)
}

val Int.second: Long
    get() {
        return TimeUnit.SECONDS.toMillis(this.toLong())
    }
val Int.minute: Long
    get() {
        return TimeUnit.MINUTES.toMillis(this.toLong())
    }
val Int.hour: Long
    get() {
        return TimeUnit.HOURS.toMillis(this.toLong())
    }
val Int.day: Long
    get() {
        return TimeUnit.DAYS.toMillis(this.toLong())
    }

