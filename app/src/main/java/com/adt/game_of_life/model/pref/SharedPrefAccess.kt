package com.adt.game_of_life.model.pref

import android.app.Application
import android.content.Context
import com.adt.game_of_life.model.pref.serializer.IGameRulesSerializer
import com.adt.game_of_life.util.edit

class SharedPrefAccess(
    private val context: Application,
    private val serializer: IGameRulesSerializer
) : IGameRulesPref, IColorsPref, IBoardPref {

    override fun getAliveColor(): Int {
        return getPrefs().getInt(ALIVE_COLOR, DEFAULT_ALIVE_COLOR)
    }

    override fun setAliveColor(color: Int) {
        getPrefs().edit {
            it.putInt(ALIVE_COLOR, color)
        }
    }

    override fun getDeadColor(): Int {
        return getPrefs().getInt(DEAD_COLOR, DEFAULT_DEAD_COLOR)
    }

    override fun setDeadColor(color: Int) {
        getPrefs().edit {
            it.putInt(DEAD_COLOR, color)
        }
    }

    private fun getPrefs() = context.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE)

    override fun setNeighboursToDie(neighboursToDie: List<Int>) {
        val serialized = serializer.serialize(neighboursToDie)
        getPrefs().edit { it.putString(NEIGHBOURS_TO_DIE, serialized) }
    }

    override fun getNeighboursToDie(): MutableList<Int> {
        val serialized = getPrefs().getString(NEIGHBOURS_TO_DIE, DEFAULT_NEIGHBOURS_TO_DIE)!!
        return serializer.deserialize(serialized)
    }

    override fun setNeighboursToBorn(neighboursToBorn: List<Int>) {
        val serialized = serializer.serialize(neighboursToBorn)
        getPrefs().edit { it.putString(NEIGHBOURS_TO_BORN, serialized) }
    }

    override fun getNeighboursToBorn(): MutableList<Int> {
        val serialized = getPrefs().getString(NEIGHBOURS_TO_BORN, DEFAULT_NEIGHBOURS_TO_BORN)!!
        return serializer.deserialize(serialized)
    }

    override fun getWidth(): Int {
        return getPrefs().getInt(BOARD_WIDTH, DEFAULT_BOARD_WIDTH)
    }

    override fun setWidth(width: Int) {
        getPrefs().edit { it.putInt(BOARD_WIDTH, width) }
    }

    override fun getHeight(): Int {
        return getPrefs().getInt(BOARD_HEIGHT, DEFAULT_BOARD_HEIGHT)
    }

    override fun setHeight(height: Int) {
        getPrefs().edit { it.putInt(BOARD_HEIGHT, height) }
    }

    private companion object {
        const val SHARED_PREFS = "GAME_OF_LIFE_SHARED_PREFERENCES"
        const val NEIGHBOURS_TO_DIE = "NEIGHBOURS_TO_DIE"
        const val NEIGHBOURS_TO_BORN = "NEIGHBOURS_TO_BORN"
        const val ALIVE_COLOR = "ALIVE_COLOR"
        const val DEAD_COLOR = "DEAD_COLOR"
        const val BOARD_WIDTH = "BOARD_WIDTH"
        const val BOARD_HEIGHT = "BOARD_HEIGHT"

        const val DEFAULT_NEIGHBOURS_TO_DIE = "0;1;4;5;6;7;8;"
        const val DEFAULT_NEIGHBOURS_TO_BORN = "3;"
        const val DEFAULT_ALIVE_COLOR = -1
        const val DEFAULT_DEAD_COLOR = -16777216
        const val DEFAULT_BOARD_WIDTH = 50
        const val DEFAULT_BOARD_HEIGHT = 50
    }
}