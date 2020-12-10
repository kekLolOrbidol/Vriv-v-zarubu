package com.adt.game_of_life.model.dto

import android.os.Bundle

data class ActivityStartModel(
    val type: Class<*>,
    val bundle: Bundle
)