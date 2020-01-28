package com.laari.rider.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.gson.GsonBuilder
import com.laari.rider.models.Cities
import com.laari.rider.models.RegistrationModel
import com.laari.rider.network.auth.*
import com.laari.rider.utility.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class SignupViewModel(val context: Application) : AndroidViewModel(context) {
    private val authAPIService = AuthApiService(context)


    val statesLiveData: LiveData<ArrayList<States>>  = authAPIService.statesLiveData
    val citiesLiveData : LiveData<ArrayList<Cities>> = authAPIService.cityLiveData
    val companyLiveData : LiveData<ArrayList<Company>> = authAPIService.companyLiveData
    val centerLiveData : LiveData<ArrayList<BookingCentre>> = authAPIService.centersLiveData
    val registerLiveData : LiveData<Any> = authAPIService.registerLiveData


    fun loadStates(id: String )= authAPIService.getStates(id)
    fun loadCities(id: String) = authAPIService.getCities(id)
    fun loadCompany(id: String) = authAPIService.getCompanies(id)
    fun loadCenters(id: String) = authAPIService.getCenters(id)
    fun registerUser(signUpRequest: SignUpRequest) = authAPIService.registerUser(signUpRequest)


}