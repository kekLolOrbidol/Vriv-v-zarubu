package com.adt.game_of_life.model.algorithm

interface IBoardManipulator {
    fun reviveCell(x: Int, y: Int): Boolean
    fun killCell(x: Int, y: Int): Boolean
    fun clear(): Array<Array<Int?>>
    fun randomize(): Array<Array<Int?>>
    fun setBoard(board: Array<Array<Int?>>)
    fun resize(width: Int, height: Int)
}