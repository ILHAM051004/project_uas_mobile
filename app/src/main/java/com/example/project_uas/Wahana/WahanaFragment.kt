package com.example.project_uas.Wahana

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.project_uas.BaseActivity
import com.example.project_uas.databinding.FragmentWahanaBinding

class WahanaFragment : Fragment() {
    private var _binding: FragmentWahanaBinding? = null
    private val binding get() = _binding!!

    // Katalog Data
    private val dataOriginal = listOf(
        "Wahana Safari", "Taman Burung", "Kolam Reptil", "Area Gajah",
        "Kandang Harimau", "Taman Kupu-Kupu", "Danau Ikan", "Unta Tunggang"
    ).sorted()

    private var dataDisplay = mutableListOf<String>()
    private lateinit var adapter: ArrayAdapter<String>
    private var isAscending = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentWahanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dataDisplay.clear()
        dataDisplay.addAll(dataOriginal)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, dataDisplay)
        binding.listViewItems.adapter = adapter

        // Fitur Pencarian
        binding.searchViewWahana?.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean = false
            override fun onQueryTextChange(newText: String?): Boolean {
                dataDisplay.clear()
                dataDisplay.addAll(dataOriginal.filter { it.contains(newText.orEmpty(), true) })
                adapter.notifyDataSetChanged()
                return true
            }
        })

        // Fitur Klik Item -> Ke Pemesanan
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