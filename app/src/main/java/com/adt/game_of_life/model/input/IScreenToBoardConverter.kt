package com.adt.game_of_life.model.input

import android.graphics.RectF
import com.adt.game_of_life.model.dto.Coords

interface IScreenToBoardConverter {
    fun convert(coords: Coords, scale: Float, displayRect: RectF): Coords
    fun convert(x: Int, y: Int, scale: Float, displayRect: RectF): Coords
}