package com.example.project_uas.Home

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_uas.Data.Api.SloganApiClient
import com.example.project_uas.Login
import com.example.project_uas.QRCode.QRCodeActivity
import com.example.project_uas.R
import com.example.project_uas.databinding.FragmentHomeBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        /** Ganti menjadi versi binding */
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Gunakan SessionManager agar datanya sama dengan saat Login
        val session = com.example.project_uas.utils.SessionManager(requireContext())
        val namaUser = session.getNama() ?: "Pengunjung" // Ambil nama yang disimpan

        // Set teks ke TextView judulWelcome yang ada di atas imgHeader
        binding.judulWelcome.text = "Welcome, $namaUser"

        // Load Slogan API
        loadSloganList()

        // untuk aktivitas tombol 1
        binding.tombol1.setOnClickListener {
            val intent = Intent(requireContext(), RumusBangunDatarBangunRuang::class.java)
            intent.putExtra("judul", "Rumus Bangun Datar dan Bangun Ruang")
            intent.putExtra("deskripsi", "Perhitungan untuk mencari bangun datar dan bangun ruang")
            startActivity(intent)
        }

        // untuk aktivitas tombol 2
        binding.tombol2.setOnClickListener {
            val intent = Intent(requireContext(), Tiket::class.java)
            intent.putExtra("judul", "Pembelian Tiket")
            intent.putExtra("deskripsi", "Beli tiket untuk masuk ke kebun binatang")
            startActivity(intent)
        }

        // untuk aktivitas tombol 3
        binding.tombol3.setOnClickListener {
            val intent = Intent(requireContext(), Info::class.java)
            intent.putExtra("judul", "Wahana Kebun Binatang")
            intent.putExtra("deskripsi", "Lihat berbagai wahana menarik di kebun binatang!")
            startActivity(intent)
        }

        // untuk aktivitas tombol webview
        binding.tombolWebView.setOnClickListener {
            val intent = Intent(requireContext(), WebView::class.java)
            startActivity(intent)
        }

        // untuk aktivitas tombol zona
        binding.zona.setOnClickListener {
            val intent = Intent(requireContext(), Zona::class.java)
            startActivity(intent)
        }

        // untuk aktivitas tombol hewan
        binding.hewan.setOnClickListener {
            val intent = Intent(requireContext(), Hewan::class.java)
            startActivity(intent)
        }

        // untuk aktivitas tombol wiki
        binding.wikiONNX.setOnClickListener {
            val intent = Intent(requireContext(), WikiActivity::class.java)
            startActivity(intent)
        }

        // untuk aktivitas tombol qr code
        binding.ScanCode.setOnClickListener {
            val intent = Intent(requireContext(), QRCodeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadSloganList() {
        lifecycleScope.launch {
            try {
                // Ganti string ini dengan Anon Key terbaru dari Dashboard Supabase Anda
                val anonKey =
                    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImN2a2hrb2pwc2Vleml4ZWNzYnVsIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NjM4ODUyMjEsImV4cCI6MjA3OTQ2MTIyMX0.ljVwoDVl0VvNJS2rSB1h4tau5Va0dBh7M_y5-5bOyeI"

                val data = SloganApiClient.api.getSlogans(
                    apiKey = anonKey,
                    token = "Bearer $anonKey"
                )

                // Gunakan requireActivity().runOnUiThread jika perlu,
                // tapi lifecycleScope.launch sudah berjalan di main thread untuk binding
                if (data.isNotEmpty()) {
                    binding.rvSlogan.apply {
                        layoutManager = LinearLayoutManager(requireContext())
                        adapter = SloganAdapter(data)
                    }
                    android.util.Log.d("API_SUCCESS", "Data berhasil dimuat: ${data.size} item")
                } else {
                    android.util.Log.d("API_SUCCESS", "Data kosong di tabel")
                }

            } catch (e: Exception) {
                // Cek pesan ini di Logcat untuk tahu alasan gagal (401=Key Salah, 404=Tabel Salah)
                android.util.Log.e("API_ERROR", "Detail Error: ${e.message}")
            }
        }
    }
}