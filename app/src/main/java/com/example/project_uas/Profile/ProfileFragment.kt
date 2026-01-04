package com.example.project_uas

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_uas.databinding.FragmentProfileBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Menginisialisasi SessionManager dari package utils
        val session = com.example.project_uas.utils.SessionManager(requireContext())

        // Menampilkan data user dari session
        binding.tvNama.text = session.getNama()
        binding.tvEmail.text = session.getEmail()
        binding.tvHp.text = session.getPhone()

        // Logika Tombol Logout dengan Dialog Konfirmasi
        binding.btnLogout.setOnClickListener {
            MaterialAlertDialogBuilder(requireContext())
                .setTitle("Logout")
                .setMessage("Apakah Anda yakin ingin logout?")
                .setPositiveButton("Ya") { dialog, _ ->
                    // Eksekusi logout dari session
                    session.logout()

                    dialog.dismiss()

                    // Berpindah ke halaman Login dan membersihkan tumpukan activity
                    val intent = Intent(requireContext(), Login::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    requireActivity().finish()
                }
                .setNegativeButton("Tidak") { dialog, _ ->
                    // Membatalkan logout dan memberi feedback snackbar
                    Snackbar.make(binding.root, "Logout dibatalkan", Snackbar.LENGTH_SHORT)
                        .setAction("Tutup") { }
                        .show()
                    dialog.dismiss()
                }
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}