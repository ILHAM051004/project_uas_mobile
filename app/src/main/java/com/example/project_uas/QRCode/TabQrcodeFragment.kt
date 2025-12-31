package com.example.project_uas.QRCode

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.project_uas.databinding.FragmentTabQrcodeBinding
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter

class TabQrcodeFragment : Fragment() {
    private var _binding: FragmentTabQrcodeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTabQrcodeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1. Ambil ID Tiket otomatis
        val idOtomatis = arguments?.getString("ID_TIKET_OTOMATIS")

        if (idOtomatis != null) {
            // 2. SEMBUNYIKAN SEMUA INPUTAN (Agar tampilan tidak seperti di screenshot)
            binding.edtQrInput.visibility = View.GONE
            binding.edtTanggal.visibility = View.GONE
            binding.edtJumlah.visibility = View.GONE
            binding.btnGenerate.visibility = View.GONE

            // 3. Langsung Generate dan Tampilkan QR
            val bitmap = createQR(idOtomatis)
            binding.ivQrCode.setImageBitmap(bitmap)
        }
    }

    // Fungsi Logika Pembuatan Gambar QR
    private fun createQR(text: String): Bitmap {
        val writer = QRCodeWriter()
        val matrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512,
            mapOf(EncodeHintType.CHARACTER_SET to "UTF-8"))
        val bitmap = Bitmap.createBitmap(512, 512, Bitmap.Config.RGB_565)
        for (x in 0 until 512) {
            for (y in 0 until 512) {
                bitmap.setPixel(x, y, if (matrix.get(x, y)) Color.BLACK else Color.WHITE)
            }
        }
        return bitmap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}