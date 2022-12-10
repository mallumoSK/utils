@file:Suppress("unused")

package tk.mallumo.utils

import android.content.*
import android.content.pm.*
import android.graphics.*
import android.graphics.drawable.*
import android.os.*
import android.util.*
import android.view.*
import androidx.core.content.*


fun Context.dp(value: Int): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), dm).toInt()

val Context.dm: DisplayMetrics get() = resources.displayMetrics

fun DisplayMetrics.valueOfDP(value: Number): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value.toFloat(), this).toInt()

fun DisplayMetrics.valueOfSP(value: Number): Int =
    TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, value.toFloat(), this).toInt()

//
val Context.sp: SPref get() = SPref[this]

val Context.inflater: LayoutInflater get() = LayoutInflater.from(this)

val View.inflater: LayoutInflater get() = context.inflater

fun Context.drawable(drawableRes: Int): Drawable = getDrawable(drawableRes)!!

fun Context.dimen(dimenRes: Int): Int = resources.getDimensionPixelSize(dimenRes)

fun Context.string(stringRes: Int, vararg args: Any = emptyArray()): String =
    if (args.isNotEmpty()) {
        resources.getString(stringRes, *args)
    } else {
        resources.getString(stringRes)
    }

fun Context.color(colorRes: Int): Int = ContextCompat.getColor(this, colorRes)

fun Context.isPermissionValid(vararg permissions: String): Boolean =
    arePermissionsValid(*permissions)


fun Context.arePermissionsValid(vararg permissions: String): Boolean {
    return permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
}

fun Context.bitmap(drawableId: Int): Bitmap {
    val drawable = drawable(drawableId)
    return if (drawable is BitmapDrawable) {
        drawable.bitmap
    } else if (drawable is VectorDrawable) {
        bitmap(drawable)
    } else {
        val canvas = Canvas()
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        canvas.setBitmap(bitmap)
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        drawable.draw(canvas)
        bitmap
    }
}

fun Context.bitmap(vectorDrawable: VectorDrawable): Bitmap {
    val bitmap = Bitmap.createBitmap(
        vectorDrawable.intrinsicWidth,
        vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888
    )
    val canvas = Canvas(bitmap)
    vectorDrawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
    vectorDrawable.draw(canvas)
    return bitmap
}

fun Context.copyToClipboard(text: String) {
    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(applicationInfo.name, text)
    clipboard.setPrimaryClip(clip)
}

