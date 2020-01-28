package com.laari.rider.network.auth

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.laari.rider.HomeBaseActivity
import com.laari.rider.models.Cities
import com.laari.rider.models.CitiesResponse
import com.laari.rider.utility.API_BASE_URL
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthApiService(application: Application)  {
    val statesLiveData: MutableLiveData<ArrayList<States>> = MutableLiveData()
    val cityLiveData: MutableLiveData<ArrayList<Cities>> = MutableLiveData()
    val companyLiveData: MutableLiveData<ArrayList<Company>> = MutableLiveData()
    val centersLiveData: MutableLiveData<ArrayList<BookingCentre>> = MutableLiveData()
    val registerLiveData: MutableLiveData<Any> = MutableLiveData()


    val loginLiveData: MutableLiveData<LoginResponse> = MutableLiveData()


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
                    println("statesError " + t.printStackTrace())
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
                            println("cityData " + list.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<CitiesResponse>, t: Throwable) {

                    t.printStackTrace()
                }
            })

    }


    fun getCompanies(id: String) {
        getAuthAPI().getCompanies("getAllCompanyByCitiesId/" + id, "")
            .enqueue(object : Callback<CompanyResponse> {
                override fun onResponse(
                    call: Call<CompanyResponse>,
                    response: Response<CompanyResponse>
                ) {
                    println("companyResponse " + response.body())
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res!!.success) {
                            val list = ArrayList(res.company)
                            companyLiveData.value = res.company
                            println("companyData " + list.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<CompanyResponse>, t: Throwable) {
                    println("companyFailure " + t.toString())
                    t.printStackTrace()
                }
            })

    }


    fun getCenters(id: String) {
        getAuthAPI().getCenters("getBookingCentreById/" + id, "")
            .enqueue(object : Callback<CenterResponse> {
                override fun onResponse(
                    call: Call<CenterResponse>,
                    response: Response<CenterResponse>
                ) {
                    println("centerResponse " + response.body())
                    if (response.isSuccessful) {
                        val res = response.body()
                        if (res!!.success) {
                            val list = ArrayList(res.bookingCentre)
                            centersLiveData.value = res.bookingCentre
                            println("centerData " + list.toString())
                        }
                    }
                }

                override fun onFailure(call: Call<CenterResponse>, t: Throwable) {
                    println("centerFailure " + t.toString())
                    t.printStackTrace()
                }
            })

    }


    fun loginUser(loginRequest: LoginRequest) {
        getAuthAPI().login(loginRequest, "").enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                t.printStackTrace()
                loginLiveData.value =
                    LoginResponse(success = false, message = "Something went wrong!")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) loginLiveData.value = response.body()
                else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    loginLiveData.value = LoginResponse(
                        success = false,
                        message = jObjError.getString("message") ?: "Something went wrong!"

                    )
                }
            }
        })
    }


    fun registerUser(signUpRequest: SignUpRequest) {
        getAuthAPI().signUp(signUpRequest, "").enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                println("registerResponse " + response.toString())
               if (response.isSuccessful) registerLiveData.value = response.body()

            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }


}
