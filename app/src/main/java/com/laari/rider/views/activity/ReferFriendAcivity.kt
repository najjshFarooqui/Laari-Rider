package com.laari.rider.views.activity


import android.os.Bundle
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivityReferFriendAcivityBinding
//import farooqui.najish.applligent.laaririder.databinding.ActivityReferFriendAcivityBinding
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*


class ReferFriendAcivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityReferFriendAcivityBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_refer_friend_acivity)
       binding.baseHandler = this
       binding.handler = this
        navReferralLayout.background =
            ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
        ivBack.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onResume() {
        super.onResume()
        navReferralLayout.background =
            ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navReferralLayout.setBackgroundResource(0)

    }
}

