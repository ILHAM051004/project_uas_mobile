package com.example.project_uas.Tiket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.project_uas.databinding.FragmentTiketBinding
import com.example.zooapp.TiketAdapter

class TiketFragment : Fragment() {
    private var _binding: FragmentTiketBinding? = null
    private val binding get() = _binding!!
    private val tiketList = listOf(
        TiketModel(
            "Tiket Safari Day",
            "Nikmati petualangan seru di kebun binatang dengan mobil safari melihat hewan dari dekat.",
            "Rp 75.000",
            "https://media-cdn.tripadvisor.com/media/attractions-splice-spp-674x446/13/76/2d/9d.jpg"
        ),
        TiketModel(
            "Tiket Anak",
            "Paket spesial untuk anak-anak di bawah 12 tahun dengan akses taman bermain dan zona edukasi.",
            "Rp 45.000",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSYvJy-FENCpB9hh2YxL2pIrfTbd9fmYfB1YQ&s"
        ),
        TiketModel(
            "Tiket Edukasi Sekolah",
            "Program pembelajaran interaktif untuk siswa dengan pemandu profesional kebun binatang.",
            "Rp 55.000",
            "https://images.unsplash.com/photo-1523875194681-bedd468c58bf"
        ),
        TiketModel(
            "Tiket VIP",
            "Akses prioritas ke seluruh area dan nikmati semuanya.",
            "Rp 120.000",
            "https://res.klook.com/image/upload/w_750,h_469,c_fill,q_85/w_80,x_15,y_15,g_south_west,l_Klook_water_br_trans_yhcmh3/activities/xepaarhevbitjbgbbdjf.jpg"
        ),
        TiketModel(
            "Tiket Reptile Zone",
            "Masuk ke dunia reptil eksotis seperti ular, iguana, dan buaya dengan area aman dan nyaman.",
            "Rp 65.000",
            "https://images.unsplash.com/photo-1571055107559-3e67626fa8be" // iguana close-up
        )
    )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_home, container, false)

        /** Ganti menjadi versi binding */
        _binding = FragmentTiketBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        // untuk toolbar
        (requireActivity() as AppCompatActivity).setSupportActionBar(binding.toolbar)
        (requireActivity() as AppCompatActivity).supportActionBar?.apply {
            title = "Tiket"
        }

        val adapter = TiketAdapter(requireContext(), tiketList)
        binding.listTiketItems.adapter = adapter
    }
}