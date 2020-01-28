package com.laari.rider.views.activity.registration

import android.os.Bundle
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R


import kotlinx.android.synthetic.main.activity_referral_code.*
import android.text.InputType
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.laari.rider.models.RegistrationModel
import com.laari.rider.network.auth.*
import com.laari.rider.utility.*
import com.laari.rider.viewmodels.SignupViewModel
import com.laari.rider.views.activity.HomeDashboardActivity
import com.laari.rider.views.adapters.CenterAdapter
import com.laari.rider.views.adapters.CompanyAdapter

class ReferralCodeActivity : HomeBaseActivity() {


    lateinit var viewModel: SignupViewModel
    var companyList: ArrayList<Company> = ArrayList()
    var centerList: ArrayList<BookingCentre>? = null
    var centerAdapter: CenterAdapter? = null
    val userData = RegistrationModel

    var center: String? = null
    var company: String? = null
    var userId: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral_code)
        val db = FirebaseFirestore.getInstance()
        val currentFirebaseUser: FirebaseUser = FirebaseAuth.getInstance().currentUser!!
        userId = currentFirebaseUser.uid
        centerList = ArrayList()
        viewModel = ViewModelProviders.of(this).get(SignupViewModel::class.java)
        viewModel.loadCompany("2465")
        viewModel.companyLiveData.observe(this, Observer {

            if (it != null) {
                it.add(0, Company("", "Companies"))
                companyList.addAll(it)

                println("najishCom " + companyList.toString())

            }


            viewModel.centerLiveData.observe(this, Observer {

                if (it != null) {
                    it.add(0, BookingCentre("", "Booking Center", ""))
                    centerList?.addAll(it)
                    centerAdapter = CenterAdapter(this, centerList!!)
                    centerSpinner!!.adapter = centerAdapter
                }

            })





            viewModel.registerLiveData.observe(this, Observer { authResponse ->
                if (authResponse != null) {

                        //Todo : Upload data to firebase

                        var user = RegistrationModel
                        val riderMap = hashMapOf(


                            Name to user.firstName,
                            LastName to user.lastName,
                            Email to user.emailAddress,
                            Address to user.address,
                            NationalId to user.nationalId,
                            PhoneNumber to user.number,
                            PhotoUrl to user.profileUrl,
                            "bookingCentreId" to user.bookingCentreId,
                            "stateId" to user.stateId,
                            "roleId" to "A",
                            "stateId" to user.stateId
                        )



                        db.collection(RIDER_NODE).document(userId!!).set(riderMap)
                            .addOnSuccessListener {
                                dismissProgress()
                                setLoggedIn(this, true)
                                startNewActivity(HomeDashboardActivity())
                            }
                            .addOnFailureListener {
                                dismissProgress()
                                Log.e("Error", it.toString())
                            }
                    } else {
                        toast("something went wrong")
                    }

            })


            val companyAdapter = CompanyAdapter(this, companyList)
            companySpinner!!.adapter = companyAdapter

            companySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {


                    companySpinner.setSelection(position)
                    val companyId = companyList.get(position).id
                    if (position == 0) {
                        companyTv.hint = null

                    } else {
                        company = companyList.get(position).companyName
                        centerList?.clear()
                        centerAdapter?.notifyDataSetChanged()
                        viewModel.loadCenters(companyId)
                        userData.company = company!!
                        userData.companyId = companyId


                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }



            centerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View,
                    position: Int,
                    id: Long
                ) {


                    if (position == 0) {
                        tvCenter.hint = null

                    } else {
                        center = centerList?.get(position)?.bookingCentreName
                        userData.bookingCenter = center!!
                        userData.bookingCentreId = centerList!!.get(position).id
                    }


                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }

        })



        referralCodeEt.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        var user = RegistrationModel


        btnFinish.setOnClickListener {
            userData.referralCode = referralCodeEt.text.toString()
            val signUpRequest = SignUpRequest(
                1,
                userId!!,
                user.firstName,
                user.lastName,
                user.emailAddress,
                user.nationalId,
                user.number,
                user.stateId,
                user.cityId,
                user.address,
                user.profileUrl,
                user.referralCode,
                user.companyId,
                user.bookingCentreId,
                "kijbekjbdkjbekjebfkjjbfkjb",
                "A"
            )
            viewModel.registerUser(signUpRequest)
        }

        btnSkip.setOnClickListener {
            toast("add your company soon")
            startNewActivity(HomeDashboardActivity())
        }
    }


}


