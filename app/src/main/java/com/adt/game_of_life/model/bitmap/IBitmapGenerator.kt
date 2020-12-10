package com.adt.game_of_life.model.bitmap

import android.graphics.Bitmap

/**
 * Created by dgrajewski on 27.03.2019.
 */
interface IBitmapGenerator {
    fun generate(board: Array<Array<Int?>>): Bitmap
}