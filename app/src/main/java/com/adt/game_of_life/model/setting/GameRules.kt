package com.adt.game_of_life.model.setting

import com.adt.game_of_life.model.pref.IGameRulesPref

class GameRules(private val prefs: IGameRulesPref) {

    private val ntd = prefs.getNeighboursToDie()
    private val ntb = prefs.getNeighboursToBorn()

    val neighboursToDie: List<Int>
        get() = ntd

    val neighboursToBorn: List<Int>
        get() = ntb

    fun addNeighbourToDie(value: Int) {
        ntd.add(value)
        prefs.setNeighboursToDie(ntd)
    }

    fun removeNeighbourToDie(value: Int) {
        ntd.remove(value)
        prefs.setNeighboursToDie(ntd)
    }

    fun addNeighbourToBorn(value: Int) {
        ntb.add(value)
        prefs.setNeighboursToBorn(ntb)
    }

    fun removeNeighbourToBorn(value: Int) {
        ntb.remove(value)
        prefs.setNeighboursToBorn(ntb)
    }
}