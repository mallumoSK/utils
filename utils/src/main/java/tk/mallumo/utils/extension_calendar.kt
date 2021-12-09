package tk.mallumo.utils

import java.util.*

fun Calendar.copy(): Calendar {
    return Calendar.getInstance().also {
        it.timeInMillis = timeInMillis
    }
}

fun Calendar.clearUpToDay() {
    set(Calendar.MILLISECOND, 0)
    set(Calendar.SECOND, 0)
    set(Calendar.MINUTE, 0)
    set(Calendar.HOUR_OF_DAY, 0)
}

fun Calendar.setEndOfMonthPreview() {
    setStartOfMonthPreview()
    add(Calendar.DAY_OF_MONTH, 41)
}

fun Calendar.setStartOfMonthPreview() {
    set(Calendar.DAY_OF_MONTH, 1)
    add(Calendar.DAY_OF_MONTH, getMonthVarStart(this))
}

fun getMonthVarStart(calendar: Calendar): Int = when (calendar.get(Calendar.DAY_OF_WEEK)) {
    Calendar.MONDAY -> 0
    Calendar.TUESDAY -> -1
    Calendar.WEDNESDAY -> -2
    Calendar.THURSDAY -> -3
    Calendar.FRIDAY -> -4
    Calendar.SATURDAY -> -5
    Calendar.SUNDAY -> -6
    else -> throw Exception("-> ? ${calendar.get(Calendar.DAY_OF_WEEK)}")
}