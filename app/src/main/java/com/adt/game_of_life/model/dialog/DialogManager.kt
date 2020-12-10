package com.adt.game_of_life.model.dialog

import android.content.Context
import com.adt.game_of_life.R
import com.adt.game_of_life.model.file.IFileManager
import com.flask.colorpicker.ColorPickerView
import com.flask.colorpicker.builder.ColorPickerDialogBuilder
import com.yarolegovich.lovelydialog.LovelyStandardDialog
import com.yarolegovich.lovelydialog.LovelyTextInputDialog
import timber.log.Timber

class DialogManager(private val fileManager: IFileManager) : IDialogManager {

    private val style = R.style.defaultDialogStyle

    override fun showSaveBoardDialog(
        context: Context,
        onConfirmButton: (String) -> Unit,
        onNegativeButton: () -> Unit
    ) {
        LovelyTextInputDialog(context, style)
            .setTopColorRes(R.color.colorAccent)
            .setTitle(R.string.dialog_save_message)
            .setInputFilter(R.string.filename_taken_or_empty) { text ->
                text.isNotBlank() && !fileManager.getFilenames().contains(text)
            }
            .setConfirmButton(R.string.dialog_ok_button) { text ->
                onConfirmButton(text)
            }
            .setNegativeButton(R.string.dialog_cancel_button) {
                onNegativeButton()
            }
            .setCancelable(false)
            .show()
    }

    override fun showDeleteSaveDialog(
        context: Context,
        onConfirmButton: () -> Unit,
        onNegativeButton: () -> Unit
    ) {
        LovelyStandardDialog(context, style)
            .setTopColorRes(R.color.colorAccent)
            .setTitle(R.string.dialog_delete_message)
            .setPositiveButton(R.string.dialog_ok_button) {
                onConfirmButton()
            }
            .setNegativeButton(R.string.dialog_cancel_button) {
                onNegativeButton()
            }
            .setCancelable(false)
            .show()
    }

    override fun showLoadDialog(
        context: Context,
        onConfirmButton: () -> Unit,
        onNegativeButton: () -> Unit
    ) {
        LovelyStandardDialog(context, style)
            .setTopColorRes(R.color.colorAccent)
            .setTitle(R.string.dialog_load_message)
            .setPositiveButton(R.string.dialog_ok_button) {
                onConfirmButton()
            }
            .setNegativeButton(R.string.dialog_cancel_button) {
                onNegativeButton()
            }
            .setCancelable(false)
            .show()
    }

    override fun showColorPicker(
        context: Context,
        initialColor: Int,
        onConfirmButton: (Int) -> Unit
    ) {
        val header = context.getString(R.string.dialog_choose_color_header)
        val ok = context.getString(R.string.dialog_ok_button)
        val cancel = context.getString(R.string.dialog_cancel_button)

        ColorPickerDialogBuilder
            .with(context, R.style.colorPickerDialogStyle)
            .setTitle(header)
            .initialColor(initialColor)
            .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
            .density(12)
            .setOnColorSelectedListener { selectedColor ->
                Timber.e(selectedColor.toString())
            }
            .setPositiveButton(ok) { _, selectedColor, _ ->
                onConfirmButton(selectedColor)
            }
            .setNegativeButton(cancel) { _, _ -> }
            .build()
            .show()
    }
}
