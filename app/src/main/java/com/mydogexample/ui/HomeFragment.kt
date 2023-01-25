package com.mydogexample.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydogexample.R
import com.mydogexample.core.Resource
import com.mydogexample.core.common.hideKeyboard
import com.mydogexample.databinding.FragmentHomeBinding
import com.mydogexample.model.data.DogsResponse
import com.mydogexample.ui.adapter.DogsAdapter
import com.mydogexample.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var imagesPuppies: List<String>
    private lateinit var dogsAdapter: DogsAdapter

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        setupBtnSearch()
    }

    private fun setupBtnSearch() {
        binding.searchBreed.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (!query.isNullOrEmpty()) {
                    viewModel.setCharacterSearched(query)
                    searchByName(query.lowercase())
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun searchByName(query: String) {
        viewModel.fetchDogByBreed(query).observe(viewLifecycleOwner) {
            when (it) {
                Resource.Loading -> {
                    Log.d("STATUSSSSS", "Loading")
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    Log.d("STATUSSSSS", "Success")
                    binding.progressBar.isVisible = false
                    if (it.data.images.isEmpty()) {
                        //binding.rvDogs.isVisible = false
                        showEmptyList(query)
                        return@observe
                    }
                    initCharacter(it.data)
                }
                is Resource.Failure -> {
                    Log.d("STATUSSSSS", "${it.exception}")
                    binding.progressBar.isVisible = false
                    showErrorDialog()
                }
            }
            binding.root.hideKeyboard()
        }
    }

    private fun initCharacter(puppies: DogsResponse) {
        if (puppies.status == "success") {
            imagesPuppies = puppies.images
        }
        dogsAdapter = DogsAdapter(imagesPuppies)
        binding.rvDogs.setHasFixedSize(true)
        binding.rvDogs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvDogs.adapter = dogsAdapter
    }

    private fun showErrorDialog() {
        Toast.makeText(requireContext(), "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun showEmptyList(query: String) {
        Toast.makeText(requireContext(), "No se encontraron imagenes de: \"$query\"", Toast.LENGTH_SHORT).show()
    }

}