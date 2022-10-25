@file:Suppress("unused")

package tk.mallumo.utils

import android.annotation.SuppressLint
import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.style.URLSpan
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.TextView


@SuppressLint("ClickableViewAccessibility")
class TextViewLinkCallback(
    val textView: TextView,
    val context: Context,
    callback: (eventType: Int, String) -> Unit
) {

    companion object {
        val EVENT_LINK_TYPE_CLICK = 0
        val EVENT_LINK_TYPE_LONG_CLICK = 1
        val EVENT_REGULAR_TYPE_LONG_CLICK = 2
    }

    init {
        textView.setOnTouchListener { _, motionEvent ->
            if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                detectorCallback.lastUrl =
                    getLinkText(textView, SpannableString.valueOf(textView.text), motionEvent)
                detectorCallback.text = textView.text.toString()
            }
            detector.onTouchEvent(motionEvent)
        }
    }

    private val detectorCallback: DetectorCallback by lazy {
        DetectorCallback(callback)
    }
    private val detector: GestureDetector by lazy {
        GestureDetector(context, detectorCallback)
    }

    private inner class DetectorCallback(
        val callback: (eventType: Int, String) -> Unit,
        var lastUrl: String = "",
        var text: String = ""
    ) : GestureDetector.SimpleOnGestureListener() {

        override fun onContextClick(e: MotionEvent) = true
        override fun onDoubleTap(e: MotionEvent?) = true
        override fun onDoubleTapEvent(e: MotionEvent?) = true
        override fun onDown(e: MotionEvent): Boolean = true


        override fun onLongPress(e: MotionEvent?) {
            super.onLongPress(e)
            if (lastUrl.isNotEmpty()) {
                callback.invoke(EVENT_LINK_TYPE_LONG_CLICK, lastUrl)
            } else if (text.isNotEmpty()) {
                callback.invoke(EVENT_REGULAR_TYPE_LONG_CLICK, text)
            }
        }


        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return if (lastUrl.isNotEmpty()) {
                callback.invoke(EVENT_LINK_TYPE_CLICK, lastUrl)
                true
            } else {
                false
            }
        }
    }

    private fun getLinkText(widget: TextView, buffer: Spannable, event: MotionEvent): String {

        var x = event.x.toInt()
        var y = event.y.toInt()

        x -= widget.totalPaddingLeft
        y -= widget.totalPaddingTop

        x += widget.scrollX
        y += widget.scrollY

        val layout = widget.layout
        val line = layout.getLineForVertical(y)
        val off = layout.getOffsetForHorizontal(line, x.toFloat())

        val link = buffer.getSpans(off, off, URLSpan::class.java)

        return if (link.isNotEmpty()) {
            link[0].url
        } else ""

    }
}

