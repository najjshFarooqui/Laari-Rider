package com.laari.rider.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.laari.rider.network.auth.*

class LoginViewModel(val context: Application) : AndroidViewModel(context) {

    private val authAPIService = AuthApiService(context)

    val authLiveDate: LiveData<LoginResponse> = authAPIService.loginLiveData



    fun loginUser(loginRequest: LoginRequest) = authAPIService.loginUser(loginRequest)



}