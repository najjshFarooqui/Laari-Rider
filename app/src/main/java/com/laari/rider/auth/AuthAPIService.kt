package com.shunan.wuthink.network.auth


import androidx.lifecycle.MutableLiveData
import com.google.gson.GsonBuilder
import com.laari.rider.models.ModelsData

import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AuthAPIService {

    val authLiveData: MutableLiveData<AuthResponse> = MutableLiveData()
    val vehicleLiveData: MutableLiveData<ArrayList<ModelsData>> = MutableLiveData()

    private fun getAuthAPI(): AuthAPI {
        val gson = GsonBuilder().setLenient().create()
        val retrofit = Retrofit.Builder().baseUrl("http://wuthink.org/survey-administrator/api/")
            .addConverterFactory(GsonConverterFactory.create(gson)).build()
        return retrofit.create<AuthAPI>(AuthAPI::class.java)
    }

    fun registerUser(signUpRequest: SignUpRequest) {
        getAuthAPI().registerUser(signUpRequest).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) authLiveData.value = response.body()
                else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    authLiveData.value = AuthResponse(
                        success = false,
                        message = jObjError.getString("message") ?: "Something went wrong!",
                        token = ""
                    )
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                authLiveData.value =
                    AuthResponse(success = false, message = "Something went wrong!", token = "")
                t.printStackTrace()
            }
        })
    }

    fun loginUser(loginRequest: LoginRequest) {
        getAuthAPI().loginUser(loginRequest).enqueue(object : Callback<AuthResponse> {
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                t.printStackTrace()
                authLiveData.value =
                    AuthResponse(success = false, message = "Something went wrong!", token = "")
            }

            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                if (response.isSuccessful) authLiveData.value = response.body()
                else {
                    val jObjError = JSONObject(response.errorBody()?.string())
                    authLiveData.value = AuthResponse(
                        success = false,
                        message = jObjError.getString("message") ?: "Something went wrong!",
                        token = ""
                    )
                }
            }
        })
    }


    fun vehicleType() {
        val list = ArrayList<ModelsData>()
        list.add(ModelsData("Skoda"))
        list.add(ModelsData("Tata"))
        list.add(ModelsData("Hyundai"))
        list.add(ModelsData("Mahindra"))
        list.add(ModelsData("Maruti Suzuki"))
        list.add(ModelsData("BMW"))
        list.add(ModelsData("Audi"))
        list.add(ModelsData("Mercedez"))
        print("najishList" + list.toString())
        vehicleLiveData.value = list

    }
}
