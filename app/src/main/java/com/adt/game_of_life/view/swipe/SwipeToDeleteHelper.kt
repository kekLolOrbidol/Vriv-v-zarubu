package com.adt.game_of_life.view.swipe

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.adt.game_of_life.view.adapter.LoadAdapter


class SwipeToDeleteHelper(
    private val adapter: LoadAdapter,
    private val icon: Drawable,
    private val backgroundColor: ColorDrawable,
    private val onSwipedCallback: (String) -> Unit
) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT.or(ItemTouchHelper.LEFT)) {

    override fun onMove(
        p0: RecyclerView,
        p1: RecyclerView.ViewHolder,
        p2: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(p0: RecyclerView.ViewHolder, direction: Int) {
        p0?.let {
            val position = p0.adapterPosition
            val element = adapter.getElement(position)
            onSwipedCallback(element)
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(
            c, recyclerView, viewHolder, dX,
            dY, actionState, isCurrentlyActive
        )

        if (viewHolder != null && c != null) {
            val itemView = viewHolder.itemView
            val backgroundCornerOffset = 20

            val iconMargin = (itemView.height - icon.intrinsicHeight) / 2
            val iconTop = itemView.top + (itemView.height - icon.intrinsicHeight) / 2
            val iconBottom = iconTop + icon.intrinsicHeight


            when {
                dX > 0 -> { // Swiping to the right
                    val iconLeft = itemView.left + iconMargin + icon.intrinsicWidth
                    val iconRight = itemView.left + iconMargin
                    icon.setBounds(iconRight, iconTop, iconLeft, iconBottom)

                    backgroundColor.setBounds(
                        itemView.left, itemView.top,
                        itemView.left + dX.toInt() + backgroundCornerOffset,
                        itemView.bottom
                    )
                }
                dX < 0 -> { // Swiping to the left
                    val iconLeft = itemView.right - iconMargin - icon.intrinsicWidth
                    val iconRight = itemView.right - iconMargin
                    icon.setBounds(iconLeft, iconTop, iconRight, iconBottom)

                    backgroundColor.setBounds(
                        itemView.right + dX.toInt() - backgroundCornerOffset,
                        itemView.top, itemView.right, itemView.bottom
                    )
                }
                else -> // view is unSwiped
                    backgroundColor.setBounds(0, 0, 0, 0)
            }

            backgroundColor.draw(c)
            icon.draw(c)
        }
    }
}