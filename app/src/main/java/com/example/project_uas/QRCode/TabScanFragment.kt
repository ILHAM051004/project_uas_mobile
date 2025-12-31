package com.example.project_uas.QRCode

import android.Manifest
import android.graphics.Color
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.databinding.FragmentTabScanBinding
import com.example.project_uas.supabase.SupabaseClient
import com.example.project_uas.Utils.PermissionHelper
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import kotlinx.coroutines.launch
import java.util.concurrent.Executors

class TabScanFragment : Fragment() {
    private var _binding: FragmentTabScanBinding? = null
    private val binding get() = _binding!!
    private var isScanning = true

    private var scanner = BarcodeScanning.getClient(
        BarcodeScannerOptions.Builder().setBarcodeFormats(Barcode.FORMAT_QR_CODE).build()
    )

    private val permissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) startCamera() else Toast.makeText(context, "Izin kamera ditolak", Toast.LENGTH_SHORT).show()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentTabScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Gunakan Helper Pertemuan 14
        if (!PermissionHelper.hasPermission(requireContext(), Manifest.permission.CAMERA)) {
            PermissionHelper.requestPermission(permissionLauncher, Manifest.permission.CAMERA)
        } else {
            startCamera()
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder().build().apply { setSurfaceProvider(binding.previewView.surfaceProvider) }
            val imageAnalyzer = ImageAnalysis.Builder().build().apply {
                setAnalyzer(Executors.newSingleThreadExecutor()) { imageProxy ->
                    val mediaImage = imageProxy.image
                    if (mediaImage != null && isScanning) {
                        val image = InputImage.fromMediaImage(mediaImage, imageProxy.imageInfo.rotationDegrees)
                        scanner.process(image).addOnSuccessListener { barcodes ->
                            if (barcodes.isNotEmpty()) verifikasiKeSupabase(barcodes[0].rawValue ?: "")
                        }.addOnCompleteListener { imageProxy.close() }
                    } else imageProxy.close()
                }
            }
            cameraProvider.bindToLifecycle(viewLifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalyzer)
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun verifikasiKeSupabase(idTiket: String) {
        isScanning = false
        lifecycleScope.launch {
            val api = TiketAPI(SupabaseClient.client)
            val tiket = api.getTiketById(idTiket) // Cek Cloud

            activity?.runOnUiThread {
                if (tiket != null) {
                    binding.tvScanResult.text = "✅ VALID: ${tiket.nama}\nJumlah: ${tiket.jumlah}"
                    binding.tvScanResult.setBackgroundColor(Color.GREEN)
                } else {
                    binding.tvScanResult.text = "❌ TIKET TIDAK TERDAFTAR"
                    binding.tvScanResult.setBackgroundColor(Color.RED)
                }
            }
            kotlinx.coroutines.delay(5000)
            isScanning = true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}