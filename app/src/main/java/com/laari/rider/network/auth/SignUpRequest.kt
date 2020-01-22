package com.laari.rider.network.auth

class SignUpRequest(


    val role: Int,
    val uId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val cnic: String,
    val phoneNumber: String,
    val cityId: Int,
    val address: String,
    val profileUrl: String,
    val referralCode: String,
    val companyId: Int,
    val bookingCentreId: Int


)