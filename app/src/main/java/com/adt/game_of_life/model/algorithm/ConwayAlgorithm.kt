package com.adt.game_of_life.model.algorithm

import com.adt.game_of_life.model.dto.BoardProperties
import com.adt.game_of_life.model.setting.GameRules

open class ConwayAlgorithm constructor(
    private val gameRules: GameRules,
    override var gameBoard: Array<Array<Int?>>
) : IConwayAlgorithm {

    private val gameBoardHeight: Int
        get() = gameBoard.size
    private val gameBoardWidth: Int
        get() = gameBoard[0].size

    protected var conwayTransitionGameBoard: Array<Array<Int?>> = gameBoard.copy()
    override val boardProperties: BoardProperties
        get() = BoardProperties(gameBoard.size, gameBoard[0].size)

    override fun gameStep(): Array<Array<Int?>> {
        for (x in 0 until gameBoardHeight) {
            for (y in 0 until gameBoardWidth) {
                updateCellLife(x, y)
            }
        }

        gameBoard = this.conwayTransitionGameBoard.copy()
        return gameBoard
    }

    private fun updateCellLife(x: Int, y: Int) {
        val cellNeighbours = countCellNeighbours(x, y)
        if (gameBoard[x][y] == 1) {
            if (gameRules.neighboursToDie.contains(cellNeighbours)) {
                conwayTransitionGameBoard[x][y] = 0
            }
        } else {
            if (gameRules.neighboursToBorn.contains(cellNeighbours)) {
                conwayTransitionGameBoard[x][y] = 1
            }
        }
    }

    private fun countCellNeighbours(x: Int, y: Int): Int {
        var liveNeighbouringCellsCounter: Int = 0

        if (x > 0 && gameBoard[x - 1][y] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (x < (gameBoardHeight - 1) && gameBoard[x + 1][y] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (y > 0 && gameBoard[x][y - 1] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (y < (gameBoardWidth - 1) && gameBoard[x][y + 1] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (x > 0 && y > 0 && gameBoard[x - 1][y - 1] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (x > 0 && y < (gameBoardWidth - 1) && gameBoard[x - 1][y + 1] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (x < (gameBoardHeight - 1) && y > 0 && gameBoard[x + 1][y - 1] == 1) {
            liveNeighbouringCellsCounter += 1
        }
        if (x < (gameBoardHeight - 1) && y < (gameBoardWidth - 1) && gameBoard[x + 1][y + 1] == 1) {
            liveNeighbouringCellsCounter += 1
        }

        return liveNeighbouringCellsCounter
    }

    protected fun Array<Array<Int?>>.copy() = Array(gameBoardHeight) { get(it).clone() }
}