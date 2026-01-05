package com.example.project_uas.Content

import ai.onnxruntime.OnnxTensor
import ai.onnxruntime.OrtEnvironment
import ai.onnxruntime.OrtSession
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.databinding.FragmentTab4WikiBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.nio.FloatBuffer

class Tab4WikiFragment : Fragment() {

    private var _binding: FragmentTab4WikiBinding? = null
    private val binding get() = _binding!!

    private lateinit var ortEnv: OrtEnvironment
    private lateinit var session: OrtSession

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTab4WikiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadModel()

        binding.btnZooPredict.setOnClickListener {
            runPrediction()
        }
    }

    private fun loadModel() {
        binding.btnZooPredict.isEnabled = false
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                ortEnv = OrtEnvironment.getEnvironment()
                val modelBytes = requireContext().assets.open("zoo_classifier_model.onnx").readBytes()
                session = ortEnv.createSession(modelBytes)
                withContext(Dispatchers.Main) { binding.btnZooPredict.isEnabled = true }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(requireContext(), "Gagal memuat model: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun runPrediction() {
        val inputs = getValidatedInputs() ?: return

        binding.btnZooPredict.isEnabled = false
        binding.zooResult.text = "Memprediksi..."

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            try {
                val tensor = OnnxTensor.createTensor(ortEnv, FloatBuffer.wrap(inputs), longArrayOf(1, 6))
                val output = session.run(mapOf(session.inputNames.first() to tensor))

                val rawLabel = output[0].value
                val predictedClass = when (rawLabel) {
                    is Array<*> -> (rawLabel[0] as Long).toInt()
                    is LongArray -> rawLabel[0].toInt()
                    is Long -> rawLabel.toInt()
                    else -> throw Exception("Format output tidak didukung: ${rawLabel::class.java}")
                }

                val animalNames = arrayOf(
                    "Mammal",
                    "Bird",
                    "Reptile",
                    "Fish",
                    "Amphibian",
                    "Bug",
                    "Invertebrate"
                )

                val resultName = if (predictedClass in animalNames.indices)
                    animalNames[predictedClass]
                else "Unknown class ($predictedClass)"

                withContext(Dispatchers.Main) {
                    binding.zooResult.text = "Hasil: $resultName"
                }

                tensor.close()
                output.close()

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    binding.zooResult.text = "Error: ${e.message}"
                }
            } finally {
                withContext(Dispatchers.Main) { binding.btnZooPredict.isEnabled = true }
            }
        }
    }

    private fun getValidatedInputs(): FloatArray? {
        val list = listOf(
            binding.etHair.text,
            binding.etFeathers.text,
            binding.etEggs.text,
            binding.etMilk.text,
            binding.etAirborne.text,
            binding.etAquatic.text
        )

        if (list.any { it.isNullOrEmpty() }) {
            Toast.makeText(requireContext(), "Semua input harus diisi!", Toast.LENGTH_SHORT).show()
            return null
        }

        return list.map { it.toString().toFloat() }.toFloatArray()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        try {
            session.close()
            ortEnv.close()
        } catch (_: Exception) {}
        _binding = null
    }
}
