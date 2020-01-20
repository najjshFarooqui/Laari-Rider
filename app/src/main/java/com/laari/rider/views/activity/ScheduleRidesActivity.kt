package com.laari.rider.views.activity

import android.os.Bundle
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.views.adapters.ScheduleRidesAdapter
import com.laari.rider.ScheduleRidesModel
import com.laari.rider.databinding.ActivityScheduleRidesBinding
import com.laari.rider.viewmodels.RidesViewModel
import kotlinx.android.synthetic.main.activity_schedule_rides.*
import kotlinx.android.synthetic.main.activity_schedule_rides.drawer_layout
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*

class ScheduleRidesActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityScheduleRidesBinding
    private lateinit var viewModel: RidesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_schedule_rides)
        binding.baseHandler = this
        binding.handler = this
        navScheduleRidesLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)
        setToolbar("Scheduled Rides")
        rvScheduledRides.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        ivBack.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }

        viewModel = ViewModelProviders.of(this).get(RidesViewModel::class.java)
        val scheduledList = viewModel.getScheduledRides().value!!

        val adapter = ScheduleRidesAdapter(scheduledList)


        rvScheduledRides.adapter = adapter
    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navScheduleRidesLayout.setBackgroundResource(0)

    }
}


