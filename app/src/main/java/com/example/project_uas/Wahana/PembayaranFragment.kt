package com.example.project_uas.Wahana

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.Home.BaseActivity
import com.example.project_uas.QRCode.TabQrcodeFragment
import com.example.project_uas.QRCode.TiketAPI
import com.example.project_uas.Utils.NotificationHelper
import com.example.project_uas.Utils.PermissionHelper
import com.example.project_uas.Utils.ReminderHelper
import com.example.project_uas.databinding.FragmentPembayaranBinding
import com.example.project_uas.supabase.SupabaseClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.launch

class PembayaranFragment : Fragment() {
    private var _binding: FragmentPembayaranBinding? = null
    private val binding get() = _binding!!

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (!isGranted) Toast.makeText(context, "Izin notifikasi ditolak", Toast.LENGTH_SHORT)
            .show()
    }

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

        if (PermissionHelper.isNotificationPermissionRequired()) {
            if (!PermissionHelper.hasPermission(
                    requireContext(),
                    Manifest.permission.POST_NOTIFICATIONS
                )
            ) {
                PermissionHelper.requestPermission(
                    requestPermissionLauncher,
                    Manifest.permission.POST_NOTIFICATIONS
                )
            }
        }

        val wahana = arguments?.getString("WAHANA") ?: "-"
        val jumlah = arguments?.getString("JUMLAH") ?: "1"
        val kategori = arguments?.getString("KATEGORI") ?: "Umum"
        val total = jumlah.toInt() * 50000

        binding.tvOrderSummary.text = "Wahana: $wahana\nKategori: $kategori\nJumlah: $jumlah Tiket"
        binding.tvTotalBayar.text = "Total: Rp $total"


        binding.btnBayarSimulasi.setOnClickListener {
            lifecycleScope.launch {
                try {
                    val api = TiketAPI(SupabaseClient.client)

                    // PENTING: Tangkap ID hasil generate dari TiketAPI
                    // Jangan buat variabel idTiket manual lagi di sini
                    val idTiketTerdaftar = api.createTiket(wahana, "2025-01-12", jumlah.toInt())

                    if (idTiketTerdaftar != null) {
                        // 1. Kirim Notifikasi Instan
                        NotificationHelper.showNotification(
                            requireContext(),
                            "Pembayaran Berhasil!",
                            "Tiket $wahana Anda sudah aktif.",
                            Intent(
                                requireContext(),
                                com.example.project_uas.QRCode.QRCodeActivity::class.java
                            )
                        )

                        // 2. Set Reminder
                        val triggerTime = System.currentTimeMillis() + 5000
                        ReminderHelper.setReminder(
                            context = requireContext(),
                            timeInMillis = triggerTime,
                            title = "ZooApp Reminder",
                            message = "Waktunya masuk ke wahana $wahana!",
                            targetActivity = com.example.project_uas.QRCode.QRCodeActivity::class.java
                        )

                        // 3. Munculkan Dialog Konfirmasi
                        MaterialAlertDialogBuilder(requireContext())
                            .setTitle("Pembayaran Berhasil!")
                            .setMessage("ID Tiket: $idTiketTerdaftar")
                            .setCancelable(false)
                            .setPositiveButton("OK") { _, _ ->
                                // Pindah ke Fragment QR Code dengan ID ASLI dari database
                                val fragmentQR = TabQrcodeFragment().apply {
                                    arguments = Bundle().apply {
                                        putString("ID_TIKET_OTOMATIS", idTiketTerdaftar)
                                        putString("WAHANA", wahana)
                                        putString("JUMLAH", jumlah)
                                        putString("KATEGORI", kategori)
                                    }
                                }
                                (activity as BaseActivity).replaceFragment(fragmentQR)
                            }.show()
                    } else {
                        Toast.makeText(
                            context,
                            "Gagal menyimpan data ke database",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                } catch (e: Exception) {
                    Toast.makeText(context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}