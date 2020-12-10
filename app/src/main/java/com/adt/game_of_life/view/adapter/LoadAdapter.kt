package com.adt.game_of_life.view.adapter

import android.support.v7.widget.AppCompatTextView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.adt.game_of_life.R

class LoadAdapter(
    private val onItemClick: (String) -> Unit
) : RecyclerView.Adapter<LoadViewHolder>() {

    private var files = listOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LoadViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.load_item, parent, false)

        return LoadViewHolder(itemView)
    }

    override fun getItemCount() = files.size

    override fun onBindViewHolder(holder: LoadViewHolder, position: Int) {
        val file = files[position]
        holder.nameTextView.text = file
        holder.setOnClickListener(onItemClick)
    }

    fun getElement(position: Int) = files[position]

    fun setElements(elements: List<String>) {
        files = elements
    }
}

class LoadViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val nameTextView: AppCompatTextView = itemView.findViewById(R.id.loadItemNameTextView)

    fun setOnClickListener(onItemClick: (String) -> Unit) {
        itemView.setOnClickListener {
            onItemClick(nameTextView.text.toString())
        }
    }
}