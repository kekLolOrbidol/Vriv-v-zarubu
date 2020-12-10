package com.adt.game_of_life.model.input

import android.graphics.RectF
import com.adt.game_of_life.model.dto.CellProperties
import com.adt.game_of_life.model.dto.Coords

class ScreenToBoardConverter(private val cell: CellProperties) : IScreenToBoardConverter {

    override fun convert(coords: Coords, scale: Float, displayRect: RectF): Coords {
        return convert(coords.x, coords.y, scale, displayRect)
    }

    override fun convert(x: Int, y: Int, scale: Float, displayRect: RectF): Coords {
        val offsetLeft = -displayRect.left / scale
        val offsetTop = -displayRect.top / scale

        val realX = x / scale + offsetLeft
        val realY = y / scale + offsetTop

        val boardX = (realX / cell.width).toInt()
        val boardY = (realY / cell.height).toInt()
        return Coords(boardX, boardY)
    }
}