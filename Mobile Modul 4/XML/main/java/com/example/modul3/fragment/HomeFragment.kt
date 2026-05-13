package com.example.modul3.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.modul3.R
import com.example.modul3.databinding.FragmentHomeBinding
import com.example.modul3.viewmodel.FoodViewModel
import com.example.modul3.viewmodel.FoodViewModelFactory
import com.example.modul3.adapter.FoodAdapter
import com.example.modul3.adapter.HighlightAdapter
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: FoodViewModel by viewModels {
        FoodViewModelFactory("Helga_Lathif")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentHomeBinding.bind(view)

        setupRecyclerView()
        observeViewModel()

        binding.btnLanguage.setOnClickListener {
            findNavController().navigate(R.id.languageFragment)
        }
    }

    private fun setupRecyclerView() {
        // Setup RecyclerView Food (Vertikal)
        binding.rvFood.layoutManager = LinearLayoutManager(requireContext())

        // Setup RecyclerView Highlight (Horizontal)
        binding.rvHighlight.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL,
            false
        )
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.foodList.collect { list ->
                // Update Adapter Food
                binding.rvFood.adapter = FoodAdapter(list) { item ->
                    Timber.d("Navigasi ke Detail: ${item.title} (ID: ${item.id})")
                    val bundle = Bundle().apply {
                        putString("title", item.title)
                        putString("description", item.description)
                        putInt("imageRes", item.imageRes)
                        putString("url", item.url)
                    }
                    findNavController().navigate(R.id.detailFragment, bundle)
                }

                // Update Adapter Highlight
                binding.rvHighlight.adapter = HighlightAdapter(list) { item ->
                    Timber.d("Highlight diklik: ${item.title}")
                    val bundle = Bundle().apply {
                        putString("title", item.title)
                        putString("description", item.description)
                        putInt("imageRes", item.imageRes)
                        putString("url", item.url)
                    }
                    findNavController().navigate(R.id.detailFragment, bundle)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}