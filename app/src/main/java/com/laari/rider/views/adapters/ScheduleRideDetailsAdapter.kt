package com.laari.rider.views.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.laari.rider.views.fragments.FinishedRidesFragment
import com.laari.rider.views.fragments.FragmentRidesCancelled

class ScheduleRideDetailsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        if (position == 0) {
            fragment = FinishedRidesFragment()
        } else if (position == 1) {
            fragment =
                FragmentRidesCancelled()
        }

        return fragment!!
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence? {
        var title: String? = null
        if (position == 0) {
            title = "Completed"
        } else if (position == 1) {
            title = "Cancelled"
        }

        return title
    }
}