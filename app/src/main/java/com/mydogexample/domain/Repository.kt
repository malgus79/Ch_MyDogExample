package com.mydogexample.domain

import com.mydogexample.model.remote.ApiService
import com.mydogexample.model.data.DogsResponse
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {

    suspend fun getCharacterByName(url: String) : DogsResponse {
        return apiService.getCharacterByName(url).body()!!
    }
}