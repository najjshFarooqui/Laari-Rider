package com.laari.rider.network.auth

class CompanyResponse( val success : Boolean ,var company  : List<Company> )


class Company ( var id : Int , var companyName : String)