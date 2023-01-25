package com.mydogexample.core

import com.mydogexample.model.data.DogsResponse

sealed class Resource<out T>() {
    object Loading : Resource<Nothing>()
    class Success<out T>(val data: DogsResponse) : Resource<T>()
    class Failure(val exception: Exception) : Resource<Nothing>()
}
