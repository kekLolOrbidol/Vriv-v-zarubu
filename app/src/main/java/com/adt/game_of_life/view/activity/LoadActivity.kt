package com.adt.game_of_life.view.activity

import android.arch.lifecycle.Observer
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.helper.ItemTouchHelper
import com.adt.game_of_life.R
import com.adt.game_of_life.databinding.ActivityLoadBinding
import com.adt.game_of_life.model.dialog.IDialogManager
import com.adt.game_of_life.model.snackbar.ISnackBarManager
import com.adt.game_of_life.util.getBinding
import com.adt.game_of_life.view.activity.contract.BackActivity
import com.adt.game_of_life.view.adapter.LoadAdapter
import com.adt.game_of_life.view.swipe.SwipeToDeleteHelper
import com.adt.game_of_life.viewmodel.LoadViewModel
import kotlinx.android.synthetic.main.activity_load.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class LoadActivity : BackActivity() {

    private val viewModel: LoadViewModel by viewModel()
    private val dialogManager: IDialogManager by inject()
    private val snackBarManager: ISnackBarManager by inject()

    private lateinit var adapter: LoadAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = getBinding<ActivityLoadBinding>(R.layout.activity_load)
        binding.vm = viewModel

        setTitle(R.string.load_activity_title)

        initializeRecyclerView()
        setObservers()
    }

    private fun setObservers() {
        viewModel.files.observe(this, Observer { files ->
            files?.let {
                adapter.setElements(it)
                adapter.notifyDataSetChanged()
            }
        })

        viewModel.snackBar.observe(this, Observer { snackbar ->
            snackbar?.let {
                snackBarManager.show(loadRootView, it)
            }
        })
    }

    private fun initializeRecyclerView() {
        adapter = LoadAdapter { filename -> showLoadDialog(filename) }
        val itemTouchHelper = createTouchHelper()
        loadRecyclerView.layoutManager = LinearLayoutManager(this)
        loadRecyclerView.adapter = adapter
        itemTouchHelper.attachToRecyclerView(loadRecyclerView)
    }

    private fun createTouchHelper(): ItemTouchHelper {
        val deleteIcon = ContextCompat.getDrawable(this, R.drawable.ic_delete_white_24dp)!!
        val deleteColor = ColorDrawable(ContextCompat.getColor(this, android.R.color.holo_red_light))

        return ItemTouchHelper(
            SwipeToDeleteHelper(
                adapter,
                deleteIcon,
                deleteColor
            ) { filename -> showDeleteDialog(filename) }
        )
    }

    private fun showLoadDialog(filename: String) {
        dialogManager.showLoadDialog(this, { viewModel.load(filename) }, {})
    }

    private fun showDeleteDialog(filename: String) {
        dialogManager.showDeleteSaveDialog(this, {
            viewModel.delete(filename)
        }, {
            adapter.notifyDataSetChanged()
        })
    }
}
