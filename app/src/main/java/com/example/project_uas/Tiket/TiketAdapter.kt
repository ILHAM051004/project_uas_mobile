package com.example.zooapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.bumptech.glide.Glide
import com.example.project_uas.Tiket.TiketModel
import com.example.project_uas.databinding.ItemTiketBinding
import com.google.android.material.snackbar.Snackbar

class TiketAdapter(
    context: Context,
    private val tiketList: List<TiketModel>
) : ArrayAdapter<TiketModel>(context, 0, tiketList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding: ItemTiketBinding =
            ItemTiketBinding.inflate(LayoutInflater.from(context), parent, false)
        val view = binding.root

        val data = tiketList[position]

        // menampilkan gambar
        Glide.with(context)
            .load(data.imageUrl)
            .into(binding.imgTiket)

        // menampilkan teks
        binding.textNamaTiket.text = data.namaTiket
        binding.textDeskripsi.text = data.deskripsi

        // tampilan ketika di klik
        view.setOnClickListener {
            Snackbar.make(
                parent,
                "Memilih ${data.namaTiket}",
                Snackbar.LENGTH_SHORT
            ).show()
        }
        return view
    }
}