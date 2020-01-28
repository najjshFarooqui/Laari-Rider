package com.laari.rider.network.auth

    data class SignUpRequest(
    val role: Int,
    val uId: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val cnic: String,
    val phoneNumber: String,
    val stateId: String,
    val cityId: String,
    val address: String,
    val profileUrl: String,
    val referralCode: String,
    val companyId: String,
    val bookingCentreId: String,
    val deviceId: String,
    val deviceType: String

    )