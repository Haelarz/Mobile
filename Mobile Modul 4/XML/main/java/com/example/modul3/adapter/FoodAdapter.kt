package com.example.modul3.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.modul3.BrowserActivity
import com.example.modul3.databinding.ItemFoodBinding
import com.example.modul3.model.FoodItem
import timber.log.Timber

class FoodAdapter(
    private val list: List<FoodItem>,
    private val onDetailClick: (FoodItem) -> Unit
) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ItemFoodBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFoodBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]

        holder.binding.tvTitle.text = item.title
        holder.binding.tvSubtitle.text = item.subtitle
        holder.binding.imgFood.setImageResource(item.imageRes)

        holder.binding.btnOpen.setOnClickListener {
            Timber.i("Tombol Explicit Intent ditekan untuk membuka halaman: ${item.url}")

            val intent = Intent(
                holder.itemView.context,
                BrowserActivity::class.java
            ).apply {
                putExtra("url", item.url)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.btnDetail.setOnClickListener {
            Timber.i("Tombol Detail ditekan untuk item ID: ${item.id}")
            Timber.d("Data dari list yang dipilih: Nama=${item.title}, Subtitle=${item.subtitle}")

            onDetailClick(item)
        }
    }
}