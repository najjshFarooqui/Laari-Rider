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
import com.laari.rider.views.adapters.RideHistoryAdapter
import com.laari.rider.databinding.ActivityRideHistoryBinding
import com.laari.rider.viewmodels.RidesViewModel
import kotlinx.android.synthetic.main.activity_home.drawer_layout
import kotlinx.android.synthetic.main.activity_ride_history.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*

class RideHistoryActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityRideHistoryBinding
    private lateinit var viewModel: RidesViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_ride_history)
        binding.baseHandler = this
        binding.handler = this
        setToolbar("Rides History")

        viewModel = ViewModelProviders.of(this).get(RidesViewModel::class.java)
        val rideHisoryList = viewModel.getRideHistory().value!!
        navRideHistoryLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)
        rvRideHistory.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)


        val adapter = RideHistoryAdapter(rideHisoryList)
        rvRideHistory.adapter = adapter

        ivBack.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onResume() {
        super.onResume()
        navRideHistoryLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)
    }

    override fun onBackPressed() {
        super.onBackPressed()


        navRideHistoryLayout.setBackgroundResource(0)

    }

}
