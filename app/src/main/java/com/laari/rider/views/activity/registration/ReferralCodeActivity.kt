package com.laari.rider.views.activity.registration

import android.os.Bundle
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.SignUpModel

import kotlinx.android.synthetic.main.activity_referral_code.*
import android.text.InputType
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.laari.rider.models.RegistrationModel
import com.laari.rider.utility.*
import com.laari.rider.views.activity.HomeDashboardActivity


class ReferralCodeActivity : HomeBaseActivity() {
    var company: String = "Applligent"
    var bookingCenter: String = "Android"
    private var userId: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_referral_code)
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
                        setLoggedIn(this,true)
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
