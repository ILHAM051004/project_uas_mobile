package com.example.project_uas.QRCode

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.*
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

        // Ambil data ID Tiket dan rincian dari Pembayaran
        val idTiket = arguments?.getString("ID_TIKET_OTOMATIS")
        val wahana = arguments?.getString("WAHANA") ?: "Wahana"
        val jumlah = arguments?.getString("JUMLAH") ?: "0"
        val kategori = arguments?.getString("KATEGORI") ?: "Umum"

        if (idTiket != null) {
            // SEMBUNYIKAN INPUTAN agar tidak seperti di screenshot
            binding.edtQrInput.visibility = View.GONE
            binding.edtTanggal.visibility = View.GONE
            binding.edtJumlah.visibility = View.GONE
            binding.btnGenerate.visibility = View.GONE

            // Tampilkan Detail & Generate QR
            binding.tvDetailTiket?.visibility = View.VISIBLE
            binding.tvDetailTiket?.text = """
                🆔 ID Tiket  : $idTiket
                🎡 Wahana    : $wahana
                👥 Kategori  : $kategori
                🎟️ Jumlah    : $jumlah Tiket
                ------------------------------------------
                Status      : VALID / LUNAS
            """.trimIndent()

            binding.ivQrCode.setImageBitmap(createQR(idTiket))
        }
    }

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