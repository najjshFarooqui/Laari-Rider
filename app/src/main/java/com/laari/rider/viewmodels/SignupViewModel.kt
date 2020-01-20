package com.laari.rider.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.laari.rider.models.RegistrationModel
import com.laari.rider.utility.*


class SignupViewModel(val context: Application) : AndroidViewModel(context) {
    lateinit var mFirestore: FirebaseFirestore
    private var userId: String = ""

    fun sendUserDetailsToFirebase(): Boolean {
        var status = false
        mFirestore = FirebaseFirestore.getInstance()
        mFirestore.firestoreSettings = FirebaseFirestoreSettings.Builder().build()
        userId = FirebaseAuth.getInstance().currentUser!!.uid

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
            mFirestore.collection(RIDER_NODE).document(userId).set(driverMap)
                .addOnSuccessListener {


                    status = true


                }
                .addOnFailureListener {



                }

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return status

    }

}