package com.adt.game_of_life.viewmodel

import android.arch.lifecycle.ViewModel
import android.os.Bundle
import com.adt.game_of_life.model.dto.ActivityStartModel
import com.adt.game_of_life.model.event.SingleLiveEvent
import com.adt.game_of_life.view.activity.AboutActivity
import com.adt.game_of_life.view.activity.GameActivity
import com.adt.game_of_life.view.activity.LoadActivity
import com.adt.game_of_life.view.activity.SettingsActivity

class MenuViewModel : ViewModel() {

    val activityToStart = SingleLiveEvent<ActivityStartModel>()

    private fun startActivity(type: Class<*>) {
        activityToStart.value = ActivityStartModel(type, Bundle())
    }

    fun startGameActivity() {
        startActivity(GameActivity::class.java)
    }

    fun startLoadActivity() {
        startActivity(LoadActivity::class.java)
    }

    fun startSettingsActivity() {
        startActivity(SettingsActivity::class.java)
    }

    fun startAboutActivity() {
        startActivity(AboutActivity::class.java)
    }
}