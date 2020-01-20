package com.laari.rider.views.activity

import android.os.Bundle
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivityAboutUsBinding


import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.nav_header.*

class AboutUsActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityAboutUsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_about_us)
        binding.baseHandler = this
        binding.handler = this
        menuIv.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }

    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onBackPressed() {
        super.onBackPressed()
        navAboutUsLayout.setBackgroundResource(0)

    }
}
