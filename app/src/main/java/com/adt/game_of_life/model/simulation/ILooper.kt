package com.adt.game_of_life.model.simulation

interface ILooper {
    fun start(callback: () -> Unit, interval: Long)
    fun stop()
}