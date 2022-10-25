package tk.mallumo.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.TypedValue


val Drawable.bitmap get() = drawableToBitmap(this)

fun drawableToBitmap(drawable: Drawable): Bitmap? {
    val bitmap: Bitmap?

    if (drawable is BitmapDrawable) {
        if (drawable.bitmap != null) {
            return drawable.bitmap
        }
    }

    if (drawable.intrinsicWidth <= 0 || drawable.intrinsicHeight <= 0) {
        bitmap = Bitmap.createBitmap(
            1,
            1,
            Bitmap.Config.ARGB_8888
        ) // Single color bitmap will be created of 1x1 pixel
    } else {
        bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    }

    val canvas = Canvas(bitmap!!)
    drawable.setBounds(0, 0, canvas.width, canvas.height)
    drawable.draw(canvas)
    return bitmap
}

/**
 * @value attrResID = android.R.attr.windowBackground
 * */
fun Context.getResIdByAttrId(attrResID: Int): Int {
    return TypedValue().also {
        theme.resolveAttribute(attrResID, it, true)
    }.resourceId
}