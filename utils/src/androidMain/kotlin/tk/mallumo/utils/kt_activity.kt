@file:Suppress("unused")

package tk.mallumo.utils


import android.app.*
import android.content.*


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
