package com.mydogexample.viewmodel

import androidx.lifecycle.*
import com.mydogexample.core.Resource
import com.mydogexample.domain.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val mutableCharacterSearched = MutableLiveData<String>()

    fun setCharacterSearched(queryCharacter: String) {
        mutableCharacterSearched.value = queryCharacter
    }

//    val fetchDogByBreed2 = mutableCharacterSearched.distinctUntilChanged().switchMap {
//        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
//            emit(Resource.Loading)
//            try {
//                emit(Resource.Success(repository.getCharacterByName("$mutableCharacterSearched/images")))
//            } catch (e: Exception) {
//                emit(Resource.Failure(e))
//            }
//        }
//    }

    fun fetchDogByBreed1(query: String) = mutableCharacterSearched.distinctUntilChanged().switchMap {
        liveData(viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(Resource.Loading)
            try {
                emit(repository.getCharacterByName("$query/images")
                    ?.let { it1 -> Resource.Success(it1) })
            } catch (e: Exception) {
                emit(Resource.Failure(e))
            }
        }
    }




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