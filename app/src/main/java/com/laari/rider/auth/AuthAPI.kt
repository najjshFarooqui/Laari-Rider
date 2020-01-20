package com.shunan.wuthink.network.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthAPI {
    @POST("register")
    fun registerUser(@Body signUpRequest: SignUpRequest): Call<AuthResponse>

    @POST("login")
    fun loginUser(@Body loginRequest: LoginRequest): Call<AuthResponse>
}