package com.example.modul3.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.modul3.BrowserActivity
import com.example.modul3.R
import com.example.modul3.databinding.FragmentDetailBinding
import timber.log.Timber

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val title = arguments?.getString("title") ?: "Nasi Goreng"
        val description = arguments?.getString("description") ?: "Menu favorit untuk sarapan"
        val imageRes = arguments?.getInt("imageRes") ?: R.drawable.mbg1
        val url = arguments?.getString("url") ?: "https://bgn.go.id/"

        binding.tvDetailTitle.text = title
        binding.tvDetailDesc.text = description
        binding.imgDetail.setImageResource(imageRes)

        binding.btnOpenBrowser.setOnClickListener {
            val intent = Intent(requireContext(), BrowserActivity::class.java).apply {
                putExtra("url", url)
            }
            startActivity(intent)
        }

        Timber.d("DetailFragment: Menampilkan data item yang dipilih: $title")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}