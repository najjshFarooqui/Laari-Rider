package com.laari.rider.views.activity

import android.os.Bundle
import android.view.Gravity
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.HomeBaseActivity
import com.laari.rider.PromosModel
import com.laari.rider.R
import com.laari.rider.databinding.ActivityPromosBinding
//import farooqui.najish.applligent.laaririder.databinding.ActivityPromosBinding
import com.laari.rider.views.adapters.PromosAdapter
import kotlinx.android.synthetic.main.activity_home.drawer_layout
import kotlinx.android.synthetic.main.activity_promos.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*

class PromosActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityPromosBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       binding = DataBindingUtil.setContentView(this, R.layout.activity_promos)
       binding.baseHandler = this
       binding.handler = this
        navPromosLayout.background =
            ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
        setToolbar("Coupon")
        ivBack.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }



        rvPromos.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val promos = ArrayList<PromosModel>()
        promos.add(
            PromosModel(
                "Coupon 30",
                "validity until 1 jan 10am"

            )
        )
        promos.add(
            PromosModel(
                "Coupon 40",
                "validity until 2 jan 10am"

            )
        )

        val adapter =
            PromosAdapter(promos)
        rvPromos.adapter = adapter


    }


override fun openDrawer() {
    drawer_layout.openDrawer(Gravity.LEFT)
}

override fun onResume() {
    super.onResume()
    navPromosLayout.background =
        ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
}


    override fun onBackPressed() {
        super.onBackPressed()


        navPromosLayout.setBackgroundResource(0)

    }
}
