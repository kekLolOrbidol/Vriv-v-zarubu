package com.adt.game_of_life.model.simulation

import android.os.Handler

class LooperImp : ILooper {

    private val handler = Handler()
    private var runnable = Runnable {}

    override fun start(callback: () -> Unit, interval: Long) {
        class SimulationRunnable : Runnable {
            override fun run() {
                callback()
                handler.postDelayed(this, interval)
            }
        }

        runnable = SimulationRunnable()
        handler.postDelayed(runnable, interval)
    }

    override fun stop() {
        handler.removeCallbacks(runnable)
    }
}