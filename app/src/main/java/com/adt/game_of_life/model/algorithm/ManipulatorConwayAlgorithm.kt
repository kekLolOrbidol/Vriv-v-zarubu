package com.adt.game_of_life.model.algorithm

import com.adt.game_of_life.model.setting.GameRules
import kotlin.random.Random

class ManipulatorConwayAlgorithm(
    gameRules: GameRules,
    gameBoard: Array<Array<Int?>>
) : ConwayAlgorithm(gameRules, gameBoard), IBoardManipulator {

    override fun clear(): Array<Array<Int?>> {
        forEachCell { y, x -> setCell(y, x, 0) }
        return gameBoard
    }

    override fun randomize(): Array<Array<Int?>> {
        forEachCell { y, x ->
            val value = Random.nextInt(0, 2)
            setCell(y, x, value)
        }
        return gameBoard
    }

    override fun reviveCell(x: Int, y: Int): Boolean {
        return setCellState(x, y, 1)
    }

    override fun killCell(x: Int, y: Int): Boolean {
        return setCellState(x, y, 0)
    }

    override fun setBoard(board: Array<Array<Int?>>) {
        gameBoard = board
        conwayTransitionGameBoard = gameBoard.copy()
    }

    override fun resize(width: Int, height: Int) {
        gameBoard = Array(height) { Array<Int?>(width) { 0 } }
        conwayTransitionGameBoard = gameBoard.copy()
    }

    private fun setCellState(x: Int, y: Int, state: Int): Boolean {
        return if (gameBoard[y][x] == state) {
            false
        } else {
            setCell(x, y, state)
            true
        }
    }

    private fun setCell(x: Int, y: Int, state: Int) {
        gameBoard[y][x] = state
        conwayTransitionGameBoard[y][x] = state
    }

    private fun forEachCell(callback: (Int, Int) -> Unit) {
        for (i in 0 until gameBoard.size) {
            for (j in 0 until gameBoard[0].size) {
                callback(j, i)
            }
        }
    }
}