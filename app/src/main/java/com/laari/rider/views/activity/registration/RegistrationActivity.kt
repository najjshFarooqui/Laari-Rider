package com.laari.rider.views.activity.registration

import android.content.Intent
import android.os.Bundle
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.SignUpModel
import com.laari.rider.models.RegistrationModel
import com.laari.rider.utility.*

import kotlinx.android.synthetic.main.activity_registration.*

class RegistrationActivity : HomeBaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        btnGoToTakePhoto.setOnClickListener {
            val firstName = firstNameEt.text.toString()
            val lastName = lastNameEt.text.toString()
            val email = emailEt.text.toString()
            val id = nationalIdEt.text.toString()

            val address = addressEt.text.toString()
            val userData = RegistrationModel

            if (firstName.isNullOrEmpty()) {
                firstNameEt.setError("please enter your name")

            } else if (lastName.isNullOrEmpty()) {
                lastNameEt.error = "please enter last name"

            } else if (!isEmailValid(email)) {
                emailEt.error = "please enter valid email"

            } else if (id.isNullOrEmpty()) {
                nationalIdEt.error = "please enter national id"



            } else if (address.isNullOrEmpty()) {
                addressEt.error = "please provide your address"

            } else {
                val intent = Intent(this, AddPhotoActivity::class.java)
                intent.putExtra(Name, firstName)
                userData.firstName = firstName
                intent.putExtra(LastName, lastName)
                userData.lastName = lastName
                intent.putExtra(Email, email)
                userData.emailAddress = email
                intent.putExtra(Id, id)
                userData.nationalId = id

                intent.putExtra(Address, address)
                userData.residentialAddress = address
                startActivity(intent)
            }


        }
    }
}
