package com.adt.game_of_life.model.snackbar

import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.adt.game_of_life.R

class SnackBarManager : ISnackBarManager {

    override fun show(view: View, model: SnackBarModel) {
        model.apply {
            val duration = if (short) Snackbar.LENGTH_SHORT else Snackbar.LENGTH_LONG
            val snackBar = Snackbar.make(view, message, duration)

            val context = view.context
            val snackBarView = snackBar.view
            val textColor = ContextCompat.getColor(context, R.color.white)

            val textView = snackBarView.findViewById<TextView>(android.support.design.R.id.snackbar_text)
            textView.setTextColor(textColor)
            
            val textSize = context.resources.getDimension(R.dimen.snackbar_text_size)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize)

            color?.let {
                val backgroundColor = ContextCompat.getColor(context, color)
                snackBarView.setBackgroundColor(backgroundColor)
            }

            snackBar.show()
        }
    }
}