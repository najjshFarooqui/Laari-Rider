package com.laari.rider.models

data class CitiesResponse(val success: Boolean, val cities: ArrayList<Cities>)

data class Cities(val id: String, val name: String, val stateId: String)