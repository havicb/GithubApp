package com.example.githubapp.presentation.main.utils

import android.content.Context
import android.os.Build
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.method.MovementMethod
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.TextView
import android.widget.ViewFlipper
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

/**
 * Converts Integer of Pixels to Integer of DP.
 * @param context Context
 * @return Int
 */
fun Number.toDp(context: Context): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        context.resources.displayMetrics
    )
        .toInt()
}

/**
 * Show the view by index.
 */
fun ViewFlipper.showViewByIndex(index: Int) =
    run { displayedChild = indexOfChild(getChildAt(index)) }

/**
 * Used to set action on [imeOptions = SEARCH]
 *
 * @param block
 */
fun EditText.onActionSearch(block: () -> Boolean) {
    setOnEditorActionListener { _, actionId, _ ->

        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            block()
            clearFocus()
        }

        false
    }
}

/**
 * Converts iso 8601 string date to date format: 2019-09-03 19:09
 */
fun String.parseIsoToReadableDate(): String {
    val date = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Date.from(Instant.parse(this))
    } else {
        this
    }

    val dateFormat = SimpleDateFormat(YYYY_MM_DD_HH_MM)

    return dateFormat.format(date)
}

/**
 * Hide view from screen.
 */
fun View.hideView() {
    visibility = View.GONE
}

/**
 * Show view on screen.
 */
fun View.showView() {
    visibility = View.VISIBLE
}

fun TextView.setClickableLink(message: String) {
    movementMethod = LinkMovementMethod.getInstance()
    text = Html.fromHtml(message)
}