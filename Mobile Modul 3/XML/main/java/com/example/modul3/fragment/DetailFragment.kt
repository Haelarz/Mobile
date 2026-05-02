package com.example.modul3.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.modul3.R
import com.example.modul3.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)

        binding.tvDetailTitle.text = "Nasi Goreng"
        binding.tvDetailDesc.text = "Menu favorit untuk sarapan"
        binding.imgDetail.setImageResource(R.drawable.mbg1)

        return binding.root
    }
}