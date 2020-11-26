package com.afolayanseyi.gaopenweather.data

data class Resource<out T> constructor(
    val state: ResourceState,
    val data: T? = null,
    val exception: Exception? = null
)

sealed class ResourceState {
    object LOADING : ResourceState()
    object SUCCESS : ResourceState()
    object ERROR : ResourceState()
}