package com.mydogexample.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydogexample.R
import com.mydogexample.core.Resource
import com.mydogexample.core.common.hide
import com.mydogexample.core.common.hideKeyboard
import com.mydogexample.core.common.show
import com.mydogexample.core.common.showToast
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
            with(binding) {
                when (it) {
                    Resource.Loading -> {
                        Log.d("STATUSSSSS", "Loading")
                        emptyContainer.root.hide()
                        progressBar.show()
                    }
                    is Resource.Success -> {
                        Log.d("STATUSSSSS", "Success")
                        progressBar.hide()
                        if (it.data.images.isEmpty()) {
                            rvDogs.hide()
                            emptyContainer.root.show()
                            showToast(getString(R.string.no_images) + query)
                            return@observe
                        }
                        initCharacter(it.data)
                    }
                    is Resource.Failure -> {
                        Log.d("STATUSSSSS", "${it.exception}")
                        progressBar.hide()
                        showToast(getString(R.string.error_ocurred))
                    }
                }
                root.hideKeyboard()
            }
        }
    }

    private fun initCharacter(puppies: DogsResponse) {
        if (puppies.status == "success") {
            imagesPuppies = puppies.images
        }
        dogsAdapter = DogsAdapter(imagesPuppies)

        binding.rvDogs.apply {
            adapter = dogsAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
            show()
        }
    }
}