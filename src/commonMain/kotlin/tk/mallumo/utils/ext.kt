@file:Suppress("unused")

package tk.mallumo.utils

import androidx.compose.runtime.*
import kotlin.math.*

fun Float.Companion.distanceBetween(x1: Float, y1: Float, x2: Float, y2: Float): Float {
    return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
}

fun Double.Companion.distanceBetween(x1: Double, y1: Double, x2: Double, y2: Double): Double {
    return sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1))
}

fun <T> MutableState<T>.asState(): State<T> = this
