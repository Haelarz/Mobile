package com.example.modul3.fragment

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul3.R
import com.example.modul3.databinding.FragmentHomeBinding
import com.example.modul3.model.FoodItem
import com.example.modul3.adapter.FoodAdapter
import com.example.modul3.adapter.HighlightAdapter

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val foodList = listOf(
        FoodItem(1,"SMP","Sarapan","Enak",R.drawable.mbg1),
        FoodItem(2,"SD","Sarapan","Sehat",R.drawable.mbg2),
        FoodItem(3,"SD","Makan Siang","Fresh",R.drawable.mbg3),
        FoodItem(4,"SMA","Makan Malam","Baik",R.drawable.mbg4),
        FoodItem(5,"SMP","Makan Siang","Pagi",R.drawable.mbg5)
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.rvFood.layoutManager =
            LinearLayoutManager(requireContext())

        binding.rvFood.adapter =
            FoodAdapter(foodList) {
                findNavController().navigate(R.id.detailFragment)
            }

        binding.rvHighlight.layoutManager =
            LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )

        binding.rvHighlight.adapter =
            HighlightAdapter(foodList) { clickedItem ->
                findNavController().navigate(R.id.detailFragment)
            }

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.languageFragment)
        }

        return binding.root
    }
}