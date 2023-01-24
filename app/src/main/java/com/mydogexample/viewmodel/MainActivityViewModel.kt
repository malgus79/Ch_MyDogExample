package com.mydogexample.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.mydogexample.core.Resource
import com.mydogexample.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    fun fetchDogByBreed(query: String) =
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(Resource.Success(repository.getCharacterByName("$query/images")!!))
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
}