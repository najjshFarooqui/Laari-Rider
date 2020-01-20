package com.laari.rider.views.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laari.rider.BaseActivity
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.utility.*
import kotlinx.android.synthetic.main.activity_ride_details.*

class RideDetailsActivity : HomeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_details)


        val bundle = intent.extras
        if (bundle != null) {
            tvRideDate.text = bundle.getString(DATE)!!
            ridePickupTime.text = bundle.getString(WAITING_TIME)!!
            tvRideDetailsFare.text = bundle.getString(FARE)!!
            tvRideDetailsDistance.text = bundle.getString(DISTANCE)!!
            tvRideDetailsTime.text = bundle.getString(TIME)!!
            tvPickup.text = bundle.getString(PICKUP)
            tvDrop.text = bundle.getString(DESTINATION)



        }


    }
}
