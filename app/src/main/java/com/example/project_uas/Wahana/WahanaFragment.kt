package com.example.project_uas.Wahana

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.TextView // TAMBAHKAN IMPORT INI AGAR TIDAK ERROR
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.databinding.FragmentWahanaBinding

class WahanaFragment : Fragment() {
    private var _binding: FragmentWahanaBinding? = null
    private val binding get() = _binding!!

    private val dataOriginal = listOf(
        "Safari Journey - Petualangan Mobil Terbuka",
        "Bird Park - Koleksi Burung Eksotis",
        "Reptile Land - Dunia Ular & Komodo",
        "Elephant Village - Interaksi Gajah Sumatra",
        "Tiger Territory - Konservasi Harimau Benggala",
        "Butterfly Garden - Taman Ribuan Kupu-kupu",
        "Primate Island - Konservasi Orangutan",
        "Camel Ride - Sensasi Tunggang Unta"
    ).sorted()

    private var dataDisplay = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWahanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataDisplay.clear()
        dataDisplay.addAll(dataOriginal)

        // Adapter dengan custom styling programmatik
        adapter = object : ArrayAdapter<String>(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataDisplay
        ) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                val view = super.getView(position, convertView, parent)

                // Mendapatkan referensi TextView bawaan Android
                val text = view.findViewById<TextView>(android.R.id.text1)

                // Custom styling agar terlihat profesional
                text.setTextColor(android.graphics.Color.parseColor("#1B5E20"))
                text.textSize = 16f
                text.setPadding(32, 32, 32, 32)

                // Membuat background kartu putih melayang (rounded)
                val shape = android.graphics.drawable.GradientDrawable()
                shape.cornerRadius = 24f
                shape.setColor(android.graphics.Color.WHITE)
                view.background = shape

                return view
            }
        }
        binding.listViewItems.adapter = adapter

        // Fitur Pencarian
        binding.searchViewWahana?.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                dataDisplay.clear()
                dataDisplay.addAll(dataOriginal.filter { it.contains(newText.orEmpty(), true) })
                adapter.notifyDataSetChanged()
                return true
            }
        })

        // Navigasi ke OrderWahanaFragment
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val fragment = OrderWahanaFragment().apply {
                arguments = Bundle().apply { putString("NAMA_WAHANA", dataDisplay[position]) }
            }
            (activity as BaseActivity).replaceFragment(fragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}