package com.laari.rider.views.activity

import android.os.Bundle
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivityNotificationBinding
//import farooqui.najish.applligent.laaririder.databinding.ActivityNotificationBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_home.drawer_layout
import kotlinx.android.synthetic.main.activity_home.menuIv
import kotlinx.android.synthetic.main.activity_notification.*
import kotlinx.android.synthetic.main.nav_header.*

class NotificationActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityNotificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_notification)
        binding.baseHandler = this
        binding.handler = this
        navNotificationLayout.background =
            ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
        menuIv.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
        toolbarTitle.text = "Notification"

    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navNotificationLayout.setBackgroundResource(0)

    }
}
