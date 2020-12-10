package com.adt.game_of_life.model.pref

interface IColorsPref {

    fun getAliveColor(): Int

    fun setAliveColor(color: Int)

    fun getDeadColor(): Int

    fun setDeadColor(color: Int)
}