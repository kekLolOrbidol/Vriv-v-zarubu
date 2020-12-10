package com.adt.game_of_life.model.pref.serializer

import java.util.*

class GameRulesSerializer : IGameRulesSerializer {

    private val delimiter = ';'

    override fun serialize(rules: List<Int>): String {
        val builder = StringBuilder()
        rules.forEach { builder.append(it).append(delimiter) }
        return builder.toString()
    }

    override fun deserialize(serialized: String): MutableList<Int> {
        val result = mutableListOf<Int>()
        val tokenizer = StringTokenizer(serialized, delimiter.toString())
        for (i in 0 until tokenizer.countTokens()) {
            val token = tokenizer.nextToken()
            val number = Integer.parseInt(token)
            result.add(number)
        }
        return result
    }
}