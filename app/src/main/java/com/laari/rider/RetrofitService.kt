package com.laari.rider

import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Retrofit


object RetrofitService {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://dummy.restapiexample.com/api/v1/employee/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()


    fun <S> cteateService(serviceClass: Class<S>): S {
        return retrofit.create(serviceClass)
    }

}