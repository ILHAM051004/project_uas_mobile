package com.example.project_uas.QRCode

import android.app.DatePickerDialog
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.databinding.FragmentTabQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.coroutines.launch
import java.util.*

class TabQrcodeFragment : Fragment() {
    private var _binding: FragmentTabQrcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTabQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDatePicker()

        binding.btnGenerate.setOnClickListener {
            val nama = binding.edtQrInput.text.toString().trim()
            val tanggal = binding.edtTanggal.text.toString().trim()
            val jumlahStr = binding.edtJumlah.text.toString().trim()
            val jumlah = jumlahStr.toIntOrNull() ?: 1

            if (nama.isEmpty() || tanggal.isEmpty()) return@setOnClickListener

            lifecycleScope.launch {
                val api = TiketAPI(SupabaseInstance.client)

                val id = api.createTiket(
                    nama = nama,
                    tanggal = tanggal,
                    jumlah = jumlah
                )

                if (id != null) {
                    binding.ivQrCode.setImageBitmap(createQR(id))
                }
            }
        }
    }

    private fun setupDatePicker() {
        binding.edtTanggal.apply {
            isFocusable = false
            isClickable = true
            setOnClickListener {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                DatePickerDialog(requireContext(), { _, y, m, d ->
                    setText(String.format("%04d-%02d-%02d", y, m + 1, d))
                }, year, month, day).show()
            }
        }
    }

    private fun createQR(text: String): Bitmap {
        val writer = QRCodeWriter()
        val matrix = writer.encode(
            text, BarcodeFormat.QR_CODE, 500, 500,
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8")
        )
        return Bitmap.createBitmap(500, 500, Bitmap.Config.RGB_565).apply {
            for (x in 0 until 500) {
                for (y in 0 until 500) {
                    setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
