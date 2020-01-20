package com.laari.rider.views.activity

import android.os.Bundle
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import kotlinx.android.synthetic.main.activity_add_card.*

class AddCardActivity : HomeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)
        setToolbar("Add Card")
        countryCodePic.setOnCountryChangeListener {
            tvSelectedCounty.text = countryCodePic.selectedCountryName
        }
    }
}
