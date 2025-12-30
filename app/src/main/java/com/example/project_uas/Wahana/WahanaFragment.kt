package com.example.project_uas.Wahana

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uas.R
import com.example.project_uas.databinding.FragmentHomeBinding
import com.example.project_uas.databinding.FragmentWahanaBinding

class WahanaFragment : Fragment() {
    private var _binding: FragmentWahanaBinding? = null
    private val binding get() = _binding!!

    // isi dari array
    private val dataList = listOf(
        "Wahana Safari",
        "Taman Burung",
        "Kolam Reptil",
        "Area Gajah",
        "Kandang Harimau",
        "Taman Kupu-Kupu",
        "Danau Ikan",
        "Wahana Unta Tunggang",
        "Kebun Primata",
        "Akuarium Air Tawar",
        "Taman Petting Zoo",
        "Kandang Singa",
        "Area Pengamatan Burung",
        "Zona Edukasi Satwa",
        "Kebun Buaya",
        "Kandang Panda",
        "Rumah Serangga",
        "Taman Herbivora",
        "Kebun Kanguru",
        "Zona Satwa Malam",
        "Wahana Berkuda",
        "Taman Flora dan Fauna",
        "Area Pemberian Makan Hewan",
        "Wahana Perahu Danau",
        "Taman Konservasi"
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        /** Ganti menjadi versi binding */
        _binding = FragmentWahanaBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // untuk toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Wahana"
        }

        // definisikan adapter sebagai penghubung dataList dengan layout
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_1,
            dataList
        )

        // Hubungkan listViewItems dengan adapter
        binding.listViewItems.adapter = adapter

        // Tambahkan aksi saat item di-list diklik
        binding.listViewItems.setOnItemClickListener { _, _, position, _ ->
            val selectedItem = dataList[position]
            Toast.makeText(requireContext(), "Memilih: $selectedItem", Toast.LENGTH_SHORT)
                .show()
        }
    }
}