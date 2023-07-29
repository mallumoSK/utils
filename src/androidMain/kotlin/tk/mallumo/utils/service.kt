package tk.mallumo.utils

import android.annotation.*
import android.content.*
import android.os.*


@Suppress("unused")
@SuppressLint("MissingPermission")
fun Context.vibrate(timeMs: Long = 20) {
    applicationContext.apply {

        @Suppress("DEPRECATION") val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    timeMs,
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(timeMs)
        }
    }
}
