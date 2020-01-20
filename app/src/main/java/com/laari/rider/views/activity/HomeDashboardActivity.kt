package com.laari.rider.views.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivitySelectCategoryBinding
import com.laari.rider.utility.ARG_FULL_LOCATION
import com.laari.rider.utility.ARG_LATITUDE
import com.laari.rider.utility.ARG_LONGITUDE
import com.laari.rider.utility.PLACE_REQUEST_CODE
import com.laari.rider.viewmodels.DashBoardViewModel
import com.laari.rider.views.adapters.FavLocationsAdapter
import kotlinx.android.synthetic.main.activity_select_category.*
import kotlinx.android.synthetic.main.nav_header.*

class HomeDashboardActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivitySelectCategoryBinding
    lateinit var viewModel: DashBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_select_category)
        binding.baseHandler = this
        binding.handler = this
        navHomeLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)

        viewModel = ViewModelProviders.of(this).get(DashBoardViewModel::class.java)
        val favInsideList = viewModel.getFavouritesInsideList().value!!
        val favOutsideList = viewModel.getFavouriteOutsideList().value!!
        rvFavInside.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        rvFavOutSide.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        val insideAdapter = FavLocationsAdapter(favInsideList, this)
        val outsideAdapter = FavLocationsAdapter(favOutsideList, this)


        rvFavInside.adapter = insideAdapter
        rvFavOutSide.adapter = outsideAdapter

        menuIv.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }
    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    fun selectedVehicleType(view: View) {
        var category: String = ""
        category = when (view.id) {

            R.id.insideCity -> "inside City"
            R.id.outsideCity -> "Outside City"

            else -> return
        }



        startActivity(Intent(this, MapsActivity::class.java).putExtra("CATEGORY", category))


    }

    override fun onResume() {
        super.onResume()
        navHomeLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        navHomeLayout.setBackgroundResource(0)

    }
}
