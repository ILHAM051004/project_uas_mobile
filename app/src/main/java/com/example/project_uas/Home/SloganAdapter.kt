package com.example.project_uas.Home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_uas.Data.Model.SloganModel
import com.example.project_uas.databinding.ItemSloganBinding

class SloganAdapter(private val items: List<SloganModel>) :
    RecyclerView.Adapter<SloganAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ItemSloganBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSloganBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.binding.tvSlogan.text = item.slogan
    }

    override fun getItemCount(): Int = items.size
}
