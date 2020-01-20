package com.laari.rider.views.activity

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivityScheduledPickupBinding
import com.laari.rider.utility.*
import com.laari.rider.views.adapters.ScheduleRideDetailsAdapter
import kotlinx.android.synthetic.main.activity_ride_details.*


import kotlinx.android.synthetic.main.activity_scheduled_pickup.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*


class ScheduledRideDetailsActivity : HomeBaseActivity() {
    private lateinit var binding: ActivityScheduledPickupBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_scheduled_pickup)
        setToolbar("Scheduled Ride Detail")

        val bundle = intent.extras
        if (bundle != null) {
            tvScheduleType.text = bundle.getString("rideType")
            tvScheduleDate.text = bundle.getString(DATE)
            tvSchedulePickup.text = bundle.getString(PICKUP)
            tvScheduleDrop.text = bundle.getString(DESTINATION)
            tvScheduleEta.text = bundle.getString(ETA)
            tvScheduleDistance.text = bundle.getString(DISTANCE)
            tvScheduleFare.text = bundle.getString(FARE)

        }


    }

}
