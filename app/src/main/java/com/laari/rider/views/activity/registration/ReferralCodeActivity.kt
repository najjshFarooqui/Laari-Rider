package com.laari.rider.views.activity.registration

import android.os.Bundle
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R


import kotlinx.android.synthetic.main.activity_referral_code.*
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.laari.rider.models.RegistrationModel
import com.laari.rider.network.auth.AuthApiService
import com.laari.rider.network.auth.States

import com.laari.rider.utility.*

import com.laari.rider.viewmodels.SignupViewModel
import com.laari.rider.views.activity.HomeDashboardActivity
import com.laari.rider.views.adapters.StatesAdapter


class ReferralCodeActivity : HomeBaseActivity() {
    var company: String = "Applligent"
    var bookingCenter: String = "Android"
    private var userId: String = ""


    lateinit var viewModel: SignupViewModel
    var states: List<States>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral_code)
        states = ArrayList()


        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)

        viewModel.loadStates("pk")


        viewModel.statesLiveData.observe(this, Observer {
            if (it != null) {
                it.add(0, States("", "States", ""))
                val statesAdapter = StatesAdapter(this, it)
                statesSpinner!!.adapter = statesAdapter

                statesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                    override fun onItemSelected(
                        parent: AdapterView<*>,
                        view: View,
                        position: Int,
                        id: Long
                    ) {

                        var state = it.get(position).name
                        var stateId = it.get(position).id
                        toast(state)

                        if (position == 0) {

                            statesTv.hint = null
                        }

                        viewModel.loadCities(stateId)




                    }

                    override fun onNothingSelected(parent: AdapterView<*>) {
                    }
                }


            } else {
                toast("null pointer")
            }
        })


        /*
        val statesAdapter = StatesAdapter(this, stateList)
        statesSpinner!!.adapter = statesAdapter

        statesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                statesSpinner.setSelection(position)
                if (position > 0) {

                    statesTv.text = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


*/


        val currentFirebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        userId = currentFirebaseUser.uid
        referralCodeEt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        val userData = RegistrationModel
        btnFinish.setOnClickListener {
            userData.referralCode = referralCodeEt.text.toString()
            //TODO : add spinner selection for company and booking center here
            userData.company = company
            userData.bookingCenter = bookingCenter

            showProgress()
            val db = FirebaseFirestore.getInstance()
            try {
                var user = RegistrationModel
                val driverMap = hashMapOf(

                    Name to user.firstName,
                    LastName to user.lastName,
                    Email to user.emailAddress,
                    Id to user.nationalId,
                    Home to user.homeCity,
                    Address to user.residentialAddress,
                    "photoUrl" to user.profilePhotoUrl,
                    "referralCode" to user.referralCode,
                    "company" to user.company,
                    "bookingCenter" to user.bookingCenter

                )
                db.collection(RIDER_NODE).document(userId).set(driverMap)
                    .addOnSuccessListener {
                        dismissProgress()
                        setLoggedIn(this, true)
                        startNewActivity(HomeDashboardActivity())
                    }
                    .addOnFailureListener {
                        dismissProgress()
                        Log.e("Error", it.toString())
                    }

            } catch (e: Exception) {
                e.printStackTrace()
            }










            btnSkip.setOnClickListener {
                toast("add your company soon")
                startNewActivity(HomeDashboardActivity())
            }
        }
    }


}
