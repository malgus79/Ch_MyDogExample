package com.mydogexample.domain

import com.mydogexample.ApiService
import com.mydogexample.DogsResponse
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCharacterByName(url: String) : DogsResponse? {
        return apiService.getCharacterByName(url).body()
    }
}