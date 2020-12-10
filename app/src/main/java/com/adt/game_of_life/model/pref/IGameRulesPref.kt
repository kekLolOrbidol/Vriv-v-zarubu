package com.adt.game_of_life.model.pref

interface IGameRulesPref {

    fun setNeighboursToDie(neighboursToDie: List<Int>)

    fun getNeighboursToDie(): MutableList<Int>

    fun setNeighboursToBorn(neighboursToBorn: List<Int>)

    fun getNeighboursToBorn(): MutableList<Int>
}