package com.laari.rider.network.auth

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.laari.rider.models.Cities
import com.laari.rider.models.CitiesResponse
import com.laari.rider.utility.API_BASE_URL
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthApiService(application: Application) {
    val statesLiveData: MutableLiveData<ArrayList<States>> = MutableLiveData()
    val cityLiveData : MutableLiveData<ArrayList<Cities>> = MutableLiveData()


    private fun getAuthAPI(): AuthAPI {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder().baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create<AuthAPI>(AuthAPI::class.java)
    }

    fun getStates(id: String) {
        getAuthAPI().getStates("getAllStatesByCountrySortName/" + id, "")
            .enqueue(object : Callback<StatesResponse> {
                override fun onResponse(
                    call: Call<StatesResponse>,
                    response: Response<StatesResponse>
                ) {
                    println("statesResponse " + response.body())
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res!!.success) {
                            val list = ArrayList(res.states)


                            statesLiveData.value = res.states
                        }
                    }
                }

                override fun onFailure(call: Call<StatesResponse>, t: Throwable) {

                    t.printStackTrace()
                }
            })

    }




    fun getCities(id: String) {
        getAuthAPI().getCities("getAllCitiesByStateId/" + id, "")
            .enqueue(object : Callback<CitiesResponse> {
                override fun onResponse(
                    call: Call<CitiesResponse>,
                    response: Response<CitiesResponse>
                ) {
                    println("citiesResponse " + response.body())
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res!!.success) {
                            val list = ArrayList(res.cities)


                            cityLiveData.value = res.cities
                        }
                    }
                }

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {

                    t.printStackTrace()
                }
            })

    }







}
