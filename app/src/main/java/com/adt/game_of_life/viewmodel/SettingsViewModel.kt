package com.adt.game_of_life.viewmodel

import android.arch.lifecycle.ViewModel
import com.adt.game_of_life.model.algorithm.IBoardManipulator
import com.adt.game_of_life.model.dto.ViewProperties
import com.adt.game_of_life.model.pref.IBoardPref
import com.adt.game_of_life.model.setting.GameColors
import com.adt.game_of_life.model.setting.GameRules

class SettingsViewModel(
    val gameRules: GameRules,
    val gameColors: GameColors,
    private val boardPref: IBoardPref,
    private val boardManipulator: IBoardManipulator
) : ViewModel() {

    val getCurrentSize: ViewProperties
        get() = ViewProperties(boardPref.getWidth(), boardPref.getHeight())

    fun setBoardWidth(width: Int) {
        boardPref.setWidth(width)
        val height = boardPref.getHeight()
        boardManipulator.resize(width, height)
    }

    fun setBoardHeight(height: Int) {
        boardPref.setHeight(height)
        val width = boardPref.getWidth()
        boardManipulator.resize(width, height)
    }
}