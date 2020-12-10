package com.adt.game_of_life.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.adt.game_of_life.R
import com.adt.game_of_life.enums.InputMode
import com.adt.game_of_life.model.algorithm.IBoardManipulator
import com.adt.game_of_life.model.algorithm.IConwayAlgorithm
import com.adt.game_of_life.model.dto.BoardProperties
import com.adt.game_of_life.model.event.SingleLiveEvent
import com.adt.game_of_life.model.file.IFileManager
import com.adt.game_of_life.model.simulation.ILooper
import com.adt.game_of_life.model.simulation.SpeedModel
import com.adt.game_of_life.model.snackbar.SnackBarModel

class GameViewModel(
    private val conwayAlgorithm: IConwayAlgorithm,
    private val boardManipulator: IBoardManipulator,
    private val looper: ILooper,
    private val speedModel: SpeedModel,
    private val fileManager: IFileManager
) : ViewModel() {

    val board = MutableLiveData<Array<Array<Int?>>>()
    val inputMode = MutableLiveData<InputMode>()
    val snackBar = SingleLiveEvent<SnackBarModel>()

    val boardProperties: BoardProperties
        get() = conwayAlgorithm.boardProperties
    val speed: Int
        get() = speedModel.percentageSpeed

    init {
        board.value = conwayAlgorithm.gameBoard
        inputMode.value = InputMode.REVIVE
    }

    fun step() {
        board.value = conwayAlgorithm.gameStep()
    }

    fun changeSpeed(percentage: Int) {
        looper.stop()
        speedModel.percentageSpeed = percentage
        if (speedModel.canRun)
            looper.start({ step() }, speedModel.interval)
    }

    fun reviveCell(x: Int, y: Int) {
        val needRedraw = boardManipulator.reviveCell(x, y)
        if (needRedraw) board.value = conwayAlgorithm.gameBoard
    }

    fun killCell(x: Int, y: Int) {
        val needRedraw = boardManipulator.killCell(x, y)
        if (needRedraw) board.value = conwayAlgorithm.gameBoard
    }

    fun switchInputMode() {
        when (inputMode.value) {
            InputMode.ZOOM -> inputMode.value = InputMode.REVIVE
            InputMode.REVIVE -> inputMode.value = InputMode.KILL
            InputMode.KILL -> inputMode.value = InputMode.ZOOM
        }
    }

    fun clear() {
        board.value = boardManipulator.clear()
    }

    fun randomize() {
        board.value = boardManipulator.randomize()
    }

    fun save(filename: String) {
        fileManager.addFile(filename, conwayAlgorithm.gameBoard)
        snackBar.value = SnackBarModel(R.string.save_saved, R.color.success)
    }

    fun permissionDenied() {
        snackBar.value = SnackBarModel(R.string.storage_permission_required, R.color.error)
    }

    fun pause() {
        speedModel.pause()
        changeSpeed(0)
    }

    fun resume() {
        val percentage = speedModel.resume()
        changeSpeed(percentage)
    }

    fun destroy() {
        looper.stop()
    }
}