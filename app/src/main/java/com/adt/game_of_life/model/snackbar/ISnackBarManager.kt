package com.adt.game_of_life.model.snackbar

import android.view.View

interface ISnackBarManager {
    fun show(view: View, model: SnackBarModel)
}