package com.laari.rider.network.auth

import com.laari.rider.models.CitiesResponse
import com.laari.rider.utility.GET_COMPANIES
import retrofit2.Call
import retrofit2.http.*

interface AuthAPI {


    @GET(GET_COMPANIES)
    fun getAllCompany()


    @GET()
    fun getStates(@Url  id : String, @Header("Authorization") auth : String) : Call<StatesResponse>






    @GET()
    fun getCities(@Url  id : String, @Header("Authorization") auth : String) : Call<CitiesResponse>





}