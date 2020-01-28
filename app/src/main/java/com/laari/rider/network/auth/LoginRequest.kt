package com.laari.rider.network.auth

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class LoginRequest(
    val role: Int,
    val phoneNumber: String,
    val deviceType: String,
    val deviceId: String
) : Parcelable