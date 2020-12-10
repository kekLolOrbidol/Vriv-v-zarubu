package com.adt.game_of_life.model.dialog

import android.content.Context

interface IDialogManager {
    fun showSaveBoardDialog(
        context: Context,
        onConfirmButton: (String) -> Unit,
        onNegativeButton: () -> Unit
    )

    fun showDeleteSaveDialog(
        context: Context,
        onConfirmButton: () -> Unit,
        onNegativeButton: () -> Unit
    )

    fun showLoadDialog(
        context: Context,
        onConfirmButton: () -> Unit,
        onNegativeButton: () -> Unit
    )

    fun showColorPicker(
        context: Context,
        initialColor: Int,
        onConfirmButton: (Int) -> Unit
    )
}