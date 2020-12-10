package com.adt.game_of_life.model.dto

class CellProperties(viewProperties: ViewProperties, boardProperties: BoardProperties) {

    val height = viewProperties.height / boardProperties.height.toFloat()
    val width = viewProperties.width / boardProperties.width.toFloat()
}