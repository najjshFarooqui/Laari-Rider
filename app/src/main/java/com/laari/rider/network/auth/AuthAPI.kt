package com.laari.rider.network.auth

import androidx.databinding.BindingMethod
import com.laari.rider.models.CitiesResponse
import com.laari.rider.utility.GET_COMPANIES
import retrofit2.Call
import retrofit2.http.*

interface AuthAPI {


    @GET()
    fun getStates(@Url id: String, @Header("Authorization") auth: String): Call<StatesResponse>


    @GET()
    fun getCities(@Url id: String, @Header("Authorization") auth: String): Call<CitiesResponse>


    @GET()
    fun getCompanies(@Url id: String, @Header("Authorization") auth: String): Call<CompanyResponse>


    @GET
    fun getCenters(@Url id: String, @Header("Authorization") auth: String): Call<CenterResponse>


    @POST("login")
    fun login(@Body body: LoginRequest, @Header("Authorization") auth: String): Call<LoginResponse>


    @POST("signup")
    fun signUp(@Body body: SignUpRequest, @Header("Authorization") auth: String): Call<Any>



}