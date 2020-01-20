package com.laari.rider.views.activity.registration


import android.content.Intent
import android.os.Bundle
import android.telephony.PhoneNumberUtils
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.utility.setLoggedIn
import com.laari.rider.utility.toast
import com.laari.rider.views.activity.HomeDashboardActivity
import com.laari.rider.views.activity.MapsActivity


import kotlinx.android.synthetic.main.activity_phone_auth.*
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : HomeBaseActivity() {
    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    var number = ""
    var code = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_phone_auth)
        FirebaseApp.initializeApp(applicationContext)
        code = countryCodePic.selectedCountryCodeWithPlus
        countryCodePic.setOnCountryChangeListener {
            code = countryCodePic.selectedCountryCodeWithPlus
        }


        firebase_auth_btn.setOnClickListener {
            showProgress()
            number = code + number_et.text.toString()



            if (PhoneNumberUtils.isGlobalPhoneNumber(number)) {
                startPhoneNumberVerification(number)
            } else {
                number_et.error = getString(R.string.invalid_number)
            }


        }
        backBtn.setOnClickListener {
            onBackPressed()
        }


        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                dismissProgress()
                Toast.makeText(applicationContext, "verification completed", Toast.LENGTH_SHORT)
                    .show()
                if (isUserAvailable()) {
                    setLoggedIn(this@PhoneAuthActivity, true)
                    startNewActivity(HomeDashboardActivity())
                    toast("welcome back")
                } else {
                    startNewActivity(RegistrationActivity())
                    toast(getString(R.string.not_a_user))
                }

            }

            override fun onVerificationFailed(p0: FirebaseException) {
                dismissProgress()
                Toast.makeText(applicationContext, "verification Failed", Toast.LENGTH_SHORT).show()
                if (p0 is FirebaseTooManyRequestsException) {
                    Toast.makeText(
                        applicationContext,
                        "you have exceeded limit, please after some time",
                        Toast.LENGTH_SHORT
                    ).show()
                }

            }


            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                dismissProgress()
                Toast.makeText(applicationContext, "code sent", Toast.LENGTH_SHORT).show()

                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID
                goToOTP()
            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                // called when the timeout duration has passed without triggering onVerificationCompleted
                dismissProgress()
                Toast.makeText(applicationContext, "time out, try resending", Toast.LENGTH_SHORT)
                    .show()


            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            phoneNumber, // Phone number to verify
            60,             // Timeout duration
            TimeUnit.SECONDS,   // Unit of timeout
            this,           // Activity (for callback binding)
            mCallbacks
        )        // OnVerificationStateChangedCallbacks
    }

    private fun isUserAvailable(): Boolean {
        return true
    }


    private fun goToOTP() {
        startActivity(
            Intent(this, OTPVerificationActivity::class.java).putExtra("NUMBER", number)
        )

    }

    override fun onResume() {
        super.onResume()
        Toast.makeText(
            applicationContext,
            countryCodePic.selectedCountryCodeWithPlus,
            Toast.LENGTH_SHORT
        ).show()
    }
}
