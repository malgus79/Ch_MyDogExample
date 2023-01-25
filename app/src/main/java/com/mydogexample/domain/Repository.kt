package com.mydogexample.domain

import com.mydogexample.core.Resource
import com.mydogexample.model.data.DogsResponse
import com.mydogexample.model.remote.RemoteDataSource
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class Repository @Inject constructor( private val remoteDataSource: RemoteDataSource) {

    suspend fun getCharacterByName(url: String) : DogsResponse {
        return remoteDataSource.getCharacterByName(url)
    }

//    fun getCharacterByName(url: String?) : Flow<Resource<List<DogsResponse>>> =
//        callbackFlow {
//            remoteDataSource.getCharacterByName(url.toString()).collectLatest {
//                when(it) {
//                    is Resource.Failure -> {}
//                    Resource.Loading -> {}
//                    is Resource.Success -> {}
//                }
//            }
//            awaitClose { cancel() }
//        }
}