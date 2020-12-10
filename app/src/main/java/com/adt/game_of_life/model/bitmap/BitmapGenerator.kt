package com.adt.game_of_life.model.bitmap

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import com.adt.game_of_life.model.dto.CellProperties
import com.adt.game_of_life.model.dto.ViewProperties
import com.adt.game_of_life.model.setting.GameColors

/**
 * Created by dgrajewski on 27.03.2019.
 */
class BitmapGenerator(
    private val gameColors: GameColors,
    private val cell: CellProperties,
    viewProperties: ViewProperties
) : IBitmapGenerator {

    private val bitmap = Bitmap.createBitmap(viewProperties.width, viewProperties.height, Bitmap.Config.RGB_565)
    private val canvas = Canvas(bitmap)
    private val rect = RectF()
    private val paint = Paint()

    init {
        paint.color = gameColors.aliveColor
    }

    override fun generate(board: Array<Array<Int?>>): Bitmap {
        canvas.drawColor(gameColors.deadColor)

        for (y in board.indices) {
            for (x in board[y].indices) {
                if (board[y][x] == 1) {
                    rect.set(
                        (x * cell.width), (y * cell.height),
                        (x * cell.width + cell.width) - 1, (y * cell.height + cell.height) - 1
                    )
                    canvas.drawRect(rect, paint)
                }
            }
        }

        return bitmap
    }
}