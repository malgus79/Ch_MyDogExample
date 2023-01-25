package com.mydogexample

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.mydogexample.core.Resource
import com.mydogexample.databinding.ActivityMainBinding
import com.mydogexample.viewmodel.MainActivityViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var imagesPuppies: List<String>
    private lateinit var dogsAdapter: DogsAdapter

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupBtnSearch()

    }

    private fun setupBtnSearch() {
        binding.searchBreed.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchByName(query!!.lowercase())
                if (query.isNotEmpty()) {
                    viewModel.setCharacterSearched(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }

    private fun searchByName(query: String) {
        viewModel.fetchDogByBreed(query).observe(this) {
            when (it) {
                Resource.Loading -> {
                    Log.d("STATUSSSSS", "Loading")
                    binding.progressBar.isVisible = true
                }
                is Resource.Success -> {
                    Log.d("STATUSSSSS", "Success")
                    binding.progressBar.isVisible = false
                    initCharacter(it.data)
                }
                is Resource.Failure -> {
                    Log.d("STATUSSSSS", "Failure")
                    binding.progressBar.isVisible = false
                    showErrorDialog()
                }
            }
            hideKeyboard()
        }
    }

    private fun initCharacter(puppies: DogsResponse) {
        if (puppies.status == "success") {
            imagesPuppies = puppies.images
        }
        dogsAdapter = DogsAdapter(imagesPuppies)
        binding.rvDogs.setHasFixedSize(true)
        binding.rvDogs.layoutManager = LinearLayoutManager(this)
        binding.rvDogs.adapter = dogsAdapter
    }

    private fun showErrorDialog() {
        Toast.makeText(this, "Ha ocurrido un error", Toast.LENGTH_SHORT).show()
    }

    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(binding.viewRoot.windowToken, 0)
    }
}