package com.example.project_uas.Wahana

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.BaseActivity
import com.example.project_uas.QRCode.TabQrcodeFragment
import com.example.project_uas.QRCode.TiketAPI
import com.example.project_uas.databinding.FragmentPembayaranBinding
import com.example.project_uas.supabase.SupabaseClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class PembayaranFragment : Fragment() {
    private var _binding: FragmentPembayaranBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPembayaranBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val wahana = arguments?.getString("WAHANA") ?: "-"
        val jumlah = arguments?.getString("JUMLAH") ?: "1"
        val kategori = arguments?.getString("KATEGORI") ?: "Umum"
        val total = jumlah.toInt() * 50000

        binding.tvOrderSummary.text = "Wahana: $wahana\nKategori: $kategori\nJumlah: $jumlah Tiket"
        binding.tvTotalBayar.text = "Total: Rp $total"

        // Di dalam PembayaranFragment.kt
        // Di dalam PembayaranFragment.kt
        binding.btnBayarSimulasi.setOnClickListener {
            val wahana = arguments?.getString("WAHANA") ?: "-"
            val jumlah = arguments?.getString("JUMLAH") ?: "1"
            // Membuat ID Tiket unik
            val idTiket = "ZOO-" + System.currentTimeMillis().toString().takeLast(6)

            lifecycleScope.launch {
                try {
                    val api = TiketAPI(SupabaseClient.client)
                    // Simpan ke Supabase
                    api.createTiket(wahana, "2025-01-12", jumlah.toInt())

                    // Notifikasi (Syarat Pertemuan 14)
                    Toast.makeText(context, "🔔 Tiket Berhasil Dipesan!", Toast.LENGTH_SHORT).show()

                    MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Pembayaran Berhasil!")
                        .setMessage("ID Tiket: $idTiket\nKlik OK untuk melihat QR Code.")
                        .setCancelable(false)
                        .setPositiveButton("OK") { _, _ ->
                            // KIRIM DATA DENGAN KEY YANG BENAR: ID_TIKET_OTOMATIS
                            val fragmentQR = TabQrcodeFragment().apply {
                                arguments = Bundle().apply {
                                    putString("ID_TIKET_OTOMATIS", idTiket)
                                }
                            }
                            (activity as BaseActivity).replaceFragment(fragmentQR)
                        }.show()
                } catch (e: Exception) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    // Di dalam PembayaranFragment.kt
    private fun pushNotification(id: String) {
        val builder = com.google.android.material.snackbar.Snackbar.make(
            binding.root,
            "🔔 Pengingat: Tiket #$id Berhasil Dipesan! Jangan telat datang ya.",
            com.google.android.material.snackbar.Snackbar.LENGTH_INDEFINITE
        )
        builder.setAction("Tutup") { builder.dismiss() }
        builder.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}