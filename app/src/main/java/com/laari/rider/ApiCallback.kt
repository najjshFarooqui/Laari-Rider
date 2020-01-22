package com.laari.rider

interface ApiCallback<T> {
    fun onStart()
    fun onError(error: Throwable)
    fun onSuccess(response: T)
}