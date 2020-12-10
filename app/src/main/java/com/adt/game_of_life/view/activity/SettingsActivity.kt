package com.adt.game_of_life.view.activity

import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.widget.CheckBox
import com.adt.game_of_life.R
import com.adt.game_of_life.databinding.ActivitySettingsBinding
import com.adt.game_of_life.model.dialog.IDialogManager
import com.adt.game_of_life.util.getBinding
import com.adt.game_of_life.util.setListener
import com.adt.game_of_life.view.activity.contract.BackActivity
import com.adt.game_of_life.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.activity_settings.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import timber.log.Timber


class SettingsActivity : BackActivity() {

    private val viewModel: SettingsViewModel by viewModel()
    private val dialogManager: IDialogManager by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = getBinding<ActivitySettingsBinding>(R.layout.activity_settings)
        binding.vm = viewModel

        setTitle(R.string.settings_activity_title)

        setupView()
        setListeners()
    }

    private fun setListeners() {
        aliveColorButton.setOnClickListener {
            dialogManager.showColorPicker(this, viewModel.gameColors.aliveColor) {
                aliveColorButton.setBackgroundColor(it)
                viewModel.gameColors.aliveColor = it
            }
        }

        deadColorButton.setOnClickListener {
            dialogManager.showColorPicker(this, viewModel.gameColors.deadColor) {
                deadColorButton.setBackgroundColor(it)
                viewModel.gameColors.deadColor = it
            }
        }

        setListeners(aliveNumbers as ConstraintLayout) { isChecked, i ->
            Timber.e("born : $isChecked : $i")
            if (isChecked)
                viewModel.gameRules.addNeighbourToBorn(i)
            else
                viewModel.gameRules.removeNeighbourToBorn(i)
        }

        setListeners(deadNumbers as ConstraintLayout) { isChecked, i ->
            Timber.e("dead : $isChecked : $i")
            if (isChecked)
                viewModel.gameRules.addNeighbourToDie(i)
            else
                viewModel.gameRules.removeNeighbourToDie(i)
        }

        widthSeekBar.setListener(widthValueTextView) {
            viewModel.setBoardWidth(it)
        }

        heightSeekBar.setListener(heightValueTextView) {
            viewModel.setBoardHeight(it)
        }
    }

    private fun setListeners(numberPicker: ConstraintLayout, listener: (Boolean, Int) -> Unit) {
        for (i in 0..8) {
            val child = numberPicker.getChildAt(i)
            if (child is CheckBox) {
                child.setOnCheckedChangeListener { _, isChecked ->
                    listener(isChecked, i)
                }
            }
        }
    }

    private fun setupView() {
        setupColorPickers()
        setupNumberPickers()
        setupSeekBars()
    }

    private fun setupColorPickers() {
        aliveColorButton.setBackgroundColor(viewModel.gameColors.aliveColor)
        deadColorButton.setBackgroundColor(viewModel.gameColors.deadColor)
    }

    private fun setupNumberPickers() {
        check(aliveNumbers as ConstraintLayout, viewModel.gameRules.neighboursToBorn)
        check(deadNumbers as ConstraintLayout, viewModel.gameRules.neighboursToDie)
    }

    private fun setupSeekBars() {
        val size = viewModel.getCurrentSize
        widthSeekBar.progress = size.width
        heightSeekBar.progress = size.height
        widthValueTextView.text = size.width.toString()
        heightValueTextView.text = size.height.toString()
    }

    private fun check(numberPicker: ConstraintLayout, checked: List<Int>) {
        for (i in 0..8) {
            val child = numberPicker.getChildAt(i)
            if (child is CheckBox && checked.contains(i)) {
                child.isChecked = true
            }
        }
    }
}
