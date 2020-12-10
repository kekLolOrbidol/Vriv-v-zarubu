package com.adt.game_of_life.util

import android.content.Context
import android.content.SharedPreferences

class VrivSharedPreferences(val context: Context) {

    var preference : SharedPreferences? = null

    fun getSharedPreference(name : String){
        preference = context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

    fun getString(name : String) : String?{
        return preference?.getString(name, null)
    }

    fun putString(name : String, value : String){
        preference?.edit()?.putString(name, value)?.apply()
    }
}
