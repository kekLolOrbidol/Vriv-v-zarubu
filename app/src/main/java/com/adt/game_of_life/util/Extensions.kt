package com.adt.game_of_life.util

import android.content.Intent
import android.content.SharedPreferences
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.AppCompatTextView
import android.view.View
import android.widget.SeekBar
import com.adt.game_of_life.model.dto.ActivityStartModel

fun AppCompatActivity.startActivity(model: ActivityStartModel) {
    val intent = Intent(this, model.type)
    intent.putExtras(model.bundle)
    startActivity(intent)
}

fun <T : ViewDataBinding> AppCompatActivity.getBinding(layout: Int): T {
    return DataBindingUtil.setContentView(this, layout)
}

fun SharedPreferences.edit(callback: (SharedPreferences.Editor) -> Unit) {
    val editor = this.edit()
    callback(editor)
    editor.commit()
}

fun View.containsPoint(x: Float, y: Float): Boolean {
    return x > 0 && x < width && y > 0 && y < height
}

fun SeekBar.setListener(textView: AppCompatTextView, offset: Int = 1, onStopTracking: (Int) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            textView.text = (progress + offset).toString()
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
            seekBar?.let {
                onStopTracking(it.progress + offset)
            }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
    })
}