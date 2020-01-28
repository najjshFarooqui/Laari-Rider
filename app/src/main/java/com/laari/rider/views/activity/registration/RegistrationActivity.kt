package com.laari.rider.views.activity.registration


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.models.Cities
import com.laari.rider.models.RegistrationModel
import com.laari.rider.network.auth.States
import com.laari.rider.utility.*
import com.laari.rider.viewmodels.SignupViewModel
import com.laari.rider.views.adapters.CityAdapter
import com.laari.rider.views.adapters.StatesAdapter

import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : HomeBaseActivity() {


    lateinit var viewModel: SignupViewModel
    var statesList: ArrayList<States> = ArrayList<States>()
    var cityList: ArrayList<Cities>? = null
    var cityAdapter: CityAdapter? = null
    val userData = RegistrationModel
    var city: String? = null
    var state: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        cityList = ArrayList()
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        val code = getCountryNameCode(this)

        viewModel.loadStates(getCountryNameCode(this)!!)
        viewModel.statesLiveData.observe(this, Observer {

            if (it != null) {
                it.add(0, States("", "States", ""))
                statesList.addAll(it)
                dismissProgress()

            }

            viewModel.citiesLiveData.observe(this, Observer { cityListResponse ->

                if (cityListResponse != null) {
                    cityListResponse.add(0, Cities("", "Cities", ""))

                    cityList?.addAll(cityListResponse)
                    cityAdapter = CityAdapter(this, cityList!!)
                    citySpinner!!.adapter = cityAdapter
                    dismissProgress()
                }

            })


            val statesAdapter = StatesAdapter(this, statesList)
            statesSpinner!!.adapter = statesAdapter

            statesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {


                    val stateId = statesList[position].id
                    if (position == 0) {
                        tvState.hint = null

                    } else {
                        state = statesList.get(position).name
                        cityList?.clear()
                        cityAdapter?.notifyDataSetChanged()
                        viewModel.loadCities(stateId)
                        showProgress()
                        userData.state = state!!
                        userData.stateId = stateId


                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
        })




        citySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {


                if (position == 0) {
                    tvCity.hint = null

                } else {

                    city = cityList?.get(position)?.name
                    userData.cityId = cityList?.get(position)?.id!!
                    userData.city = city!!
                }


            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }



        btnGoToTakePhoto.setOnClickListener {
            val firstName = firstNameEt.text.toString()
            val lastName = lastNameEt.text.toString()
            val email = emailEt.text.toString()
            val id = nationalIdEt.text.toString()


            val address = addressEt.text.toString()


            if (firstName.isEmpty()) {
                firstNameEt.setError("please enter your name")

            } else if (lastName.isEmpty()) {
                lastNameEt.error = "please enter last name"

            } else if (!isEmailValid(email)) {
                emailEt.error = "please enter valid email"

            } else if (id.isEmpty()) {
                nationalIdEt.error = "please enter national id"


            } else if (address.isEmpty()) {
                addressEt.error = "please provide your address"

            } else if (city.isNullOrEmpty()) {
                toast("Please select your city")
            } else if (state.isNullOrEmpty()) {
                toast("please select your state")
            } else {
                val intent = Intent(this, AddPhotoActivity::class.java)
                userData.firstName = firstName
                userData.lastName = lastName
                userData.emailAddress = email
                userData.nationalId = id
                userData.address = address
                startActivity(intent)
            }


        }
    }
}
