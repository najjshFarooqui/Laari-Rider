package com.laari.rider.network.auth

data class SignUpResponse(val data: UserData, val success: Boolean, val message: String)

data class UserData(
    var id: String,
    var uId: String,
    var firstName: String,
    var lastName: String,
    var email: String,
    var phoneNumber: String,
    var status: String,
    var profileUrl: String,
    var cnic: String,
    var dob: String,
    var stateId: String,
    var cityId: String,
    var address: String,
    var roleId: String,
    var referralCode: String,
    var companyId: String,
    var bookingCentreId: String,
    var createDate: String,
    var tokenId: String,
    var token: String,
    var deviceId: String,
    var deviceType: String
)

