package com.mydogexample.model.remote

import com.mydogexample.core.Resource
import com.mydogexample.model.data.DogsResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    suspend fun getCharacterByName(url: String?) : DogsResponse {
        return apiService.getCharacterByName(url.toString())
    }


//    fun getCharacterByName(url: String?) : Flow<Resource<List<DogsResponse>>> =
//        callbackFlow {
//            trySend(
//                Resource.Success(
//                apiService.getCharacterByName(url.toString())
//            ))
//            awaitClose { close() }
//        }
}