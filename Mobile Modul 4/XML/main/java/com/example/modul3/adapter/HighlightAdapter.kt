package com.example.modul3.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3.databinding.ItemHighlightBinding
import com.example.modul3.model.FoodItem

class HighlightAdapter(
    private val list: List<FoodItem>,
    private val onItemClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<HighlightAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemHighlightBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemHighlightBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.binding.imgHighlight.setImageResource(item.imageRes)

        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }
}