package com.adt.game_of_life.model.pref

interface IBoardPref {

    fun getWidth(): Int

    fun setWidth(width: Int)

    fun getHeight(): Int

    fun setHeight(height: Int)
}