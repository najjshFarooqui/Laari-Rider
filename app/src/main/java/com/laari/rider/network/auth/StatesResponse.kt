package com.laari.rider.network.auth

import kotlinx.android.parcel.Parcelize


data class StatesResponse(var success: Boolean, var states: ArrayList<States>)

data class States(var id: String, var name: String, var countryId: String)
