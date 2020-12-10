package com.adt.game_of_life.model.setting

import com.adt.game_of_life.model.pref.IColorsPref

class GameColors(private val prefs: IColorsPref) {

    var deadColor = prefs.getDeadColor()
        set(value) {
            prefs.setDeadColor(value)
            field = value
        }

    var aliveColor = prefs.getAliveColor()
        set(value) {
            prefs.setAliveColor(value)
            field = value
        }
}