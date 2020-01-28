package com.laari.rider.network.auth

data class CenterResponse(val success : Boolean , val bookingCentre : ArrayList<BookingCentre>)


data class BookingCentre(val id : String, val bookingCentreName : String , val companyId : String)