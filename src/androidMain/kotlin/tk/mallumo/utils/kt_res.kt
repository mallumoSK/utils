package tk.mallumo.utils

import android.content.*
import android.util.*

/**
 * @value attrResID = android.R.attr.windowBackground
 * */
fun Context.getResIdByAttrId(attrResID: Int): Int {
    return TypedValue().also {
        theme.resolveAttribute(attrResID, it, true)
    }.resourceId
}
