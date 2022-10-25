@file:Suppress("unused")

package tk.mallumo.utils


import android.app.Activity
import android.content.Context
import android.content.Intent


fun Intent.startActivity(activity: Activity, requestCode: Int = -1) {
    if (requestCode < 0) {
        activity.startActivity(this)
    } else {
        activity.startActivityForResult(this, requestCode)
    }
}


inline fun <reified T : Activity> Activity.startActivity() {
    startActivity(createIntent<T>())
}

inline fun <reified T : Activity> Context.createIntent() =
    Intent(this, T::class.java)
