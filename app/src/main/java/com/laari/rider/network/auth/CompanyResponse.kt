package com.laari.rider.network.auth

data class CompanyResponse( val success : Boolean ,var company  : ArrayList<Company> )


data class Company ( var id : String , var companyName : String)