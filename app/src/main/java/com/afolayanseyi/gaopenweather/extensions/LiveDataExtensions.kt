package com.afolayanseyi.gaopenweather.extensions

import androidx.lifecycle.MutableLiveData
import com.afolayanseyi.gaopenweather.data.Resource
import com.afolayanseyi.gaopenweather.data.ResourceState

fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T) =
    postValue(Resource(ResourceState.SUCCESS, data))

fun <T> MutableLiveData<Resource<T>>.setLoading() =
    postValue(Resource(ResourceState.LOADING, value?.data))

fun <T> MutableLiveData<Resource<T>>.setError(exception: Exception) =
    postValue(Resource(ResourceState.ERROR, value?.data, exception))
