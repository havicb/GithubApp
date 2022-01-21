package com.example.githubapp.presentation.main.utils

import android.content.Context
import android.util.TypedValue
import android.widget.ViewFlipper
import java.text.SimpleDateFormat
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
