@file:Suppress("unused")

package tk.mallumo.utils

import android.content.*
import android.content.pm.*
import androidx.core.content.*


//
val Context.sp: SPref get() = SPref[this]

fun Context.isPermissionValid(vararg permissions: String): Boolean =
    arePermissionsValid(*permissions)


fun Context.arePermissionsValid(vararg permissions: String): Boolean {
    return permissions.all { ContextCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED }
}

fun Context.copyToClipboard(text: String) {
    val clipboard: ClipboardManager =
        getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    val clip = ClipData.newPlainText(applicationInfo.name, text)
    clipboard.setPrimaryClip(clip)
}

