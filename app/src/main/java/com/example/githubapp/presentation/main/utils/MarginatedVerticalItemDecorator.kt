package com.example.githubapp.presentation.main.utils

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

/**
 * Applied to recycler view, add margin betwen RV items.
 * @param context
 * @param verticalMargin -> Dp number for margin between items.
 */
class MarginatedVerticalItemDecorator(
    val context: Context,
    val verticalMargin: Int
) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        rect: Rect,
        view: View,
        parent: RecyclerView,
        s: RecyclerView.State
    ) {
        parent.adapter?.let { adapter ->
            rect.bottom = when (parent.getChildAdapterPosition(view)) {
                RecyclerView.NO_POSITION, adapter.itemCount - 1 -> 0
                else -> verticalMargin.toDp(context)
            }
        }
    }
}