package com.example.project_uas.Wahana

import android.os.Bundle
import android.view.*
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.databinding.FragmentOrderWahanaBinding

class OrderWahanaFragment : Fragment() {
    private var _binding: FragmentOrderWahanaBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentOrderWahanaBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set Spinner Kategori secara programmatik agar tidak error
        val listKategori = listOf("Dewasa", "Anak-anak")
        val adapterSpinner = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, listKategori)
        adapterSpinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerKategori.adapter = adapterSpinner

        val namaWahana = arguments?.getString("NAMA_WAHANA") ?: "Wahana"
        binding.tvNamaWahanaOrder.text = namaWahana

        binding.btnProsesOrder.setOnClickListener {
            val jumlah = binding.etJumlahOrder.text.toString()
            val kategori = binding.spinnerKategori.selectedItem.toString()

            if (jumlah.isEmpty()) {
                binding.etJumlahOrder.error = "Isi jumlah tiket!"
                return@setOnClickListener
            }

            // Lanjut ke Kriteria Pembayaran & E-Ticket
            val fragmentBayar = PembayaranFragment().apply {
                arguments = Bundle().apply {
                    putString("WAHANA", namaWahana)
                    putString("JUMLAH", jumlah)
                    putString("KATEGORI", kategori)
                }
            }
            (activity as BaseActivity).replaceFragment(fragmentBayar)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}