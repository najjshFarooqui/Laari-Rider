package com.laari.rider.views.activity.registration

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.utility.setLoggedIn
import com.laari.rider.utility.toast
import com.laari.rider.views.activity.MapsActivity
import com.laari.rider.views.activity.registration.ReferralCodeActivity
import kotlinx.android.synthetic.main.activity_otpverification.*
import java.util.concurrent.TimeUnit


class OTPVerificationActivity : HomeBaseActivity() {

    var number = ""
    var otp = ""
    lateinit var firebaseAuthSettings: FirebaseAuthSettings

    lateinit var mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otpverification)
        FirebaseApp.initializeApp(this)

        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuthSettings = firebaseAuth.firebaseAuthSettings


        val bundle = intent.extras
        if (bundle != null) {
            number = bundle.getString("NUMBER")!!
        }
        enterCodeSentTo.text = getString(R.string.enter_the_code_send_to, number)


        var text1 = ""
        var text2 = ""
        var text3 = ""
        var text4 = ""
        var text5 = ""
        var text6 = ""


        et1.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    text1 = et1.text.toString()
                    et2.requestFocus()
                } else if (s.isEmpty()) {
                    et1.clearFocus()
                }
            }
        })

        et2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    text2 = et2.text.toString()
                    et3.requestFocus()
                } else if (s.isEmpty()) {
                    et1.requestFocus()
                }
            }
        })

        et3.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    text3 = et3.text.toString()
                    et4.requestFocus()
                } else if (s.isEmpty()) {
                    et2.requestFocus()
                }
            }
        })

        et4.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    text4 = et4.text.toString()
                    et5.requestFocus()

                } else if (s.isEmpty()) {
                    et3.requestFocus()
                }
            }
        })



        et5.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    text5 = et5.text.toString()
                    et6.requestFocus()
                } else if (s.isEmpty()) {
                    et4.requestFocus()
                }
            }
        })






        et6.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable) {
                if (s.length == 1) {
                    text6 = et6.text.toString()
                    otp = text1 + text2 + text3 + text4 + text5 + text6
                    showProgress()
                    verifyOtp()
                } else if (s.isEmpty()) {
                    et5.requestFocus()
                }
            }
        })



        btnVerifyOTP.setOnClickListener {

            if (otp.length != 6) {
                Toast.makeText(
                    this,
                    "otp length must be at least of 6 characters",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                verifyOtp()
            }
        }

        mCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {

                Toast.makeText(applicationContext, "verification completed", Toast.LENGTH_SHORT)
                    .show()
                Handler().postDelayed({
                    dismissProgress()
                }, 4000)
                if (isUserAvailable()) {
                    setLoggedIn(this@OTPVerificationActivity, true)
                    startNewActivity(MapsActivity())
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
                Handler().postDelayed({
                    dismissProgress()
                }, 4000)
                Toast.makeText(applicationContext, "code sent", Toast.LENGTH_SHORT).show()

                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID

            }

            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                // called when the timeout duration has passed without triggering onVerificationCompleted
                dismissProgress()
                Toast.makeText(applicationContext, "time out, try resending", Toast.LENGTH_SHORT)
                    .show()


            }
        }




        resend_otp_tv.setOnClickListener {
            showProgress()
            startPhoneNumberVerification(number)
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


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        val mAuth = FirebaseAuth.getInstance()
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(
                this
            ) { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    user!!.getIdToken(true)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {

                                if (isUserAvailable()) {
                                    setLoggedIn(this@OTPVerificationActivity, true)
                                    dismissProgress()
                                    startNewActivity(MapsActivity())
                                    toast("welcome back")
                                } else {
                                    startNewActivity(RegistrationActivity())
                                    toast(getString(R.string.not_a_user))
                                }
                            } else {
                                dismissProgress()


                            }
                        }
                }
            }
    }





    private fun verifyOtp() {
        firebaseAuthSettings.setAutoRetrievedSmsCodeForPhoneNumber(number, otp)
        val phoneAuthProvider = PhoneAuthProvider.getInstance()
        phoneAuthProvider.verifyPhoneNumber(
            number,
            60L,
            TimeUnit.SECONDS,
            this,
            object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationFailed(e: FirebaseException) {
                    if (e is FirebaseAuthInvalidCredentialsException) {
                        dismissProgress()
                        toast(e.toString())
                    } else if (e is FirebaseTooManyRequestsException) {
                        dismissProgress()
                        toast(e.toString())
                    }
                }


                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    signInWithPhoneAuthCredential(credential)

                }
            })
    }

    private fun isUserAvailable(): Boolean {
        //Todo call old or existing user api
        return false
    }

}
