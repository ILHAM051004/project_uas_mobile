package com.example.project_uas.Home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.project_uas.Data.Api.HarimauApiClient
import com.example.project_uas.databinding.FragmentHewanHarimauBinding
import kotlinx.coroutines.launch

class HewanHarimau : Fragment() {

    private var _binding: FragmentHewanHarimauBinding? = null
    private val binding get() = _binding!!

    private val apiKey = "Q2lBbaKeQZKezz6YCVyNQg==fXYdwWxpTYGFYfgP"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHewanHarimauBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //api
        loadHarimau()
    }

    private fun loadHarimau() {
        viewLifecycleOwner.lifecycleScope.launch {
            try {
                val response = HarimauApiClient.apiService.getAnimalsByName(
                    "tiger",
                    apiKey
                )

                if (response.isNotEmpty()) {
                    val tiger = response[0]

                    binding.tvAnimalName.text = tiger.name ?: "-"
                    binding.tvAnimalLocation.text =
                        tiger.locations?.joinToString(", ") ?: "-"
                } else {
                    binding.tvAnimalName.text = "Tidak ada data"
                }

            } catch (e: Exception) {
                binding.tvAnimalName.text = "Error mengambil data"
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}