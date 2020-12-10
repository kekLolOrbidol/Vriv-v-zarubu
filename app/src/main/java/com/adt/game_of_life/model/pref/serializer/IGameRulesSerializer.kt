package com.adt.game_of_life.model.pref.serializer

interface IGameRulesSerializer {

    fun serialize(rules: List<Int>): String

    fun deserialize(serialized: String): MutableList<Int>
}