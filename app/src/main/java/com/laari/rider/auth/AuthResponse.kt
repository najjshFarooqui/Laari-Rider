package com.shunan.wuthink.network.auth

import com.laari.rider.UserData


class AuthResponse(
    var message: String,
    var userData: UserData? = null,
    var success: Boolean = false,
    var token: String)