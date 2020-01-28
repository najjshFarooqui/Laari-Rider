package com.laari.rider.views.activity

import android.Manifest
import android.animation.ValueAnimator
import android.app.ActivityManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.location.Location
import android.os.Bundle
import android.os.Handler
import android.os.Parcelable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.AdapterView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.PermissionChecker
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.nabinbhandari.android.permissions.PermissionHandler
import com.nabinbhandari.android.permissions.Permissions
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivityHomeBinding
import com.laari.rider.models.*
import com.laari.rider.utility.*
import com.laari.rider.viewmodels.HomeViewModel
import com.laari.rider.views.adapters.*
import com.laari.rider.views.adapters.SelectVehicleAdapter
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.bottom_buttons.*
import kotlinx.android.synthetic.main.ceremony_details_layout.*
import kotlinx.android.synthetic.main.ceremony_details_layout.tvVehicleType
import kotlinx.android.synthetic.main.dialog_choose_vehicle.*
import kotlinx.android.synthetic.main.dialog_driver_found.*
import kotlinx.android.synthetic.main.dialog_ride_detail.*
import kotlinx.android.synthetic.main.dialog_searching_driver.*
import kotlinx.android.synthetic.main.layout_outside.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.pick_location_box.*
import kotlinx.android.synthetic.main.rental_details_layout.*
import kotlinx.android.synthetic.main.search_location_box.*
import kotlinx.android.synthetic.main.search_location_box.pic_address_tv
import org.jetbrains.anko.textColor


class MapsActivity : HomeBaseActivity(),
    HomeInterface, OnMapReadyCallback, RideOptionsInterface {


    private var mMapFragment: SupportMapFragment? = null
    private var mGoogleMap: GoogleMap? = null
    private var driverMarker: Marker? = null
    private var fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var locationRequest: LocationRequest? = null
    private var prevLatLng: LatLng? = null
    private var innerMarker: Marker? = null
    private var polyline: Polyline? = null


    private var pickupMarker: Marker? = null
    private var destMarker: Marker? = null
    private var hardcodePickup = false
    private var pickUpLatLng: LatLng? = null
    private var destLatLng: LatLng? = null


    private val package_name = getCurrentActivityName()
    private lateinit var binding: ActivityHomeBinding
    var category: String? = null
    lateinit var viewModel: HomeViewModel


    private var vehicleType: String = ""
    private var vehicleMake: String = ""
    private var vehicleModel: String = ""
    private var vehicleColor: String = ""

    private val SEARCH_DELAY: Long = 2000


    private val isLocationServiceRunning: Boolean
        get() {
            val manager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
            for (service in manager.getRunningServices(Integer.MAX_VALUE)) {
                if (package_name == service.service.className) {
                    Log.d(
                        "LocationRunning",
                        "isLocationServiceRunning: location service is already running."
                    )
                    return true
                }
            }
            Log.d(
                "LocationNotRunning",
                "isLocationServiceRunning: location service is not running."
            )
            return false
        }

    override fun onMapReady(googleMap: GoogleMap?) {
        try {
            mGoogleMap = googleMap
            mGoogleMap!!.isMyLocationEnabled = true
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)
        binding.baseHandler = this
        binding.handler = this
        binding.rideOption = 1
        locationBox.visibility = View.VISIBLE

        navHomeLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)

        mMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mMapFragment!!.getMapAsync(this)

        callPermissions()
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        viewModel.getRoute().observe(this, Observer {

            if (it != null) {
                polyline = mGoogleMap?.addPolyline(it)
            }
        })


        val typesList = viewModel.getTypesList().value as ArrayList<VehicleTypeSData>
        val makersList = viewModel.getMakersList().value as ArrayList<VehicleMakersData>
        val modelList = viewModel.getModelsList().value as ArrayList<ModelsData?>
        val colorList = viewModel.getColorsList().value as ArrayList<VehicleColorsData>

        val vehicleList = viewModel.getVehicleList().value as ArrayList<AvailableVehicleModel>


        val typesAdapter = VehicleTypesAdapter(this, typesList)
        val makersAdapter = VehicleMakersAdapter(this, makersList)
        val modelsAdapter = VehicleModelsAdapter(this, modelList)
        val colorAdapter = VehicleColorAdapter(this, colorList)

        typeVehSpinner!!.adapter = typesAdapter
        makeVehSpinner.adapter = makersAdapter
        modelVehSpinner!!.adapter = modelsAdapter
        colorVehSpinner.adapter = colorAdapter

        rvVehiclesList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val adapter = SelectVehicleAdapter(this, vehicleList!!)
        rvVehiclesList.adapter = adapter


        typeVehSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                typeVehSpinner.setSelection(position)
                if (position > 0) {
                    vehicleType = typesList.get(position).typeName
                    tvVehicleType.text = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        makeVehSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                makeVehSpinner.setSelection(position)
                if (position > 0) {
                    vehicleMake = makersList.get(position).makersName
                    tvVehicleMake.text = ""

                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }



        modelVehSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                modelVehSpinner.setSelection(position)
                if (position > 0) {
                    vehicleModel = modelList[position]!!.Model_Name
                    tvVehicleModel.text = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }



        btnRideLater.setOnClickListener {
            showDatePicker(pic_address_tv)
        }

        colorVehSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                colorVehSpinner.setSelection(position)
                if (position > 0) {
                    vehicleColor = colorList.get(position).color
                    tvColor.text = ""
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }


        ivMenu.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)

        }
        menuIv.setOnClickListener {
            drawer_layout.openDrawer(GravityCompat.START)
        }


        val bundle = intent.extras
        if (bundle != null) {
            category = bundle.getString("CATEGORY")!!
            Toast.makeText(this, category, Toast.LENGTH_SHORT).show()
            when {
                category.equals("inside City") -> {
                    toggle()
                    mapsToolbar.visibility = View.VISIBLE
                    locationBox.visibility = View.VISIBLE
                    //Todo call api related to Regular vehicle

                }
                category.equals("Outside City") -> {
                    toggle()
                    rental.visibility = View.VISIBLE
                    pickLocationBox.visibility = View.VISIBLE
                    tvRentalDateTime.setOnClickListener {
                        showDatePicker(tvRentalDateTime)
                    }
                    //Todo call api related to Rental vehicle

                }
                category.equals("Ceremony") -> {
                    toggle()
                    locationBox.visibility = View.VISIBLE
                    ceremonyLayout.visibility = View.VISIBLE
                    tvCeremonyDateTime.setOnClickListener {
                        showDatePicker(tvCeremonyDateTime)
                    }
                    //Todo call api related to Ceremony vehicle

                }
                category.equals("Outside") -> {
                    toggle()
                    locationBox.visibility = View.VISIBLE
                    layoutOutside.visibility = View.VISIBLE
                    tvOutsideDateTime.setOnClickListener {
                        showDatePicker(tvOutsideDateTime)
                    }
                    tvOutsideReturnTime.setOnClickListener {
                        showDatePicker(tvOutsideReturnTime)
                    }
                    //Todo call api related to Outside vehicle
                }
            }

        }
        btnRideNow.setOnClickListener {
            if (pic_address_tv.text.isEmpty()) {
                toast("please enter pickup address first")
            } else if (drop_address_tv.text.isEmpty()) {
                toast("please enter drop address ")
            } else {
                toggle()
                dialog_choose_vehicle.visibility = View.VISIBLE
                locationBox.visibility = View.VISIBLE
            }

            btnChooseVehicleContinue.setOnClickListener {
                toggle()
                dialog_vehicle_details.visibility = View.VISIBLE
                locationBox.visibility = View.VISIBLE
            }


            btnRequestRide.setOnClickListener {
                toggle()
                dialog_searching_driver.visibility = View.VISIBLE
                locationBox.visibility = View.VISIBLE

            }

            btnCancelRide.setOnClickListener {
                toggle()
                dialog_driver_found.visibility = View.VISIBLE
                locationBox.visibility = View.VISIBLE
            }




            btnShareRide.setOnClickListener {
                toggle()
                dialog_ride_started.visibility = View.VISIBLE
                Handler().postDelayed({
                    startNewActivity(RideFinishedActivity())
                }, 3000)

            }

        }


    }


    fun setLocationMarker(isSource: Boolean, location: String) {
        polyline?.remove()
        if (isSource) {
            pickupMarker?.remove()
            val markerOption = MarkerOptions().position(pickUpLatLng!!)
                .title("Pickup Location")
            pic_address_tv.text = location
            picLocationTv.text = location
            pickupMarker = this.mGoogleMap?.addMarker(markerOption)
            if (destLatLng == null) {


            }
        } else {
            destMarker?.remove()
            val markerOption = MarkerOptions().position(destLatLng!!)
                .icon(bitmapDescriptorFromVector(R.drawable.ic_pin))
                .title("Destination Location")
            drop_address_tv.text = location
            destMarker = this.mGoogleMap?.addMarker(markerOption)
            if (pickUpLatLng == null) {
                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(destLatLng, 11f)
                mGoogleMap?.animateCamera(cameraUpdate)

            }
        }

        if (destLatLng != null && pickUpLatLng != null) {
            val builder = LatLngBounds.Builder()
            builder.include(pickUpLatLng)
            builder.include(destLatLng)
            mGoogleMap?.setPadding(128, 512, 128, 278)
            mGoogleMap?.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 0))
            val url = getDirectionsUrl(pickUpLatLng!!, destLatLng!!)
            viewModel.getRoute(url)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PLACE_REQUEST_CODE && data != null) {
            val isSource = data.getBooleanExtra(ARG_IS_SOURCE, false)
            hardcodePickup = isSource

            val lat = data.getDoubleExtra(ARG_LATITUDE, 0.0)
            val lng = data.getDoubleExtra(ARG_LONGITUDE, 0.0)
            val location = data.getStringExtra(ARG_FULL_LOCATION)

            if (isSource) {
                pickUpLatLng = LatLng(lat, lng)
            } else {
                destLatLng = LatLng(lat, lng)
            }

            setLocationMarker(isSource, location)
        }
    }


    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onResume() {
        super.onResume()
        navHomeLayout.background =
            ResourcesCompat.getDrawable(resources, R.mipmap.side_menu_selected, null)

        startLocationService()


        val mMessageReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val message = intent.getStringExtra("Status")
                val b = intent.getBundleExtra("Location")
                val lastKnownLoc = b!!.getParcelable<Parcelable>("Location") as Location?
                val new = LatLng(lastKnownLoc!!.latitude, lastKnownLoc.longitude)
                mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(new, 15.5f))
                if (prevLatLng == null) {
                    driverMarker?.remove()
                    innerMarker = mGoogleMap!!.addMarker(
                        MarkerOptions().position(new).icon(
                            BitmapDescriptorFactory.fromResource(R.mipmap.car_marker)
                        )
                    )
                    prevLatLng = LatLng(lastKnownLoc.latitude, lastKnownLoc.longitude)
                } else {
                    val displacement = getDistance(prevLatLng!!, new)

                    if (displacement > 15) {
                        animateCarOnMap(prevLatLng!!, new)
                        mGoogleMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(new, 15.5f))
                    }
                    prevLatLng = new
                }


            }
        }

        LocalBroadcastManager.getInstance(this).registerReceiver(
            mMessageReceiver, IntentFilter("GPSLocationUpdates")
        )
    }

    private fun startLocationService() {
        if (!isLocationServiceRunning) {
            val serviceIntent = Intent(this, com.laari.rider.services.LocationService::class.java)
            //        this.startService(serviceIntent);

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {

                this@MapsActivity.startForegroundService(serviceIntent)
            } else {
                startService(serviceIntent)
            }
        }
    }

    fun requestLocationUpdates() {
        if ((ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED) and (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PermissionChecker.PERMISSION_GRANTED)
        ) {
            fusedLocationProviderClient = FusedLocationProviderClient(this)
            locationRequest = LocationRequest()
            locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            locationRequest!!.fastestInterval = 5000
            locationRequest!!.interval = 10000
            fusedLocationProviderClient!!.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        Log.d("MY_LAT ", locationResult!!.lastLocation.latitude.toString())

                        Log.d("MY_LAN ", locationResult.lastLocation.longitude.toString())

                        var currentLocation = getAddress(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude
                        )
                        // Todo: put current location in session
                        // pic_address_tv.text = currentLocation
                        // picLocationTv.text = currentLocation
                        var current = SimpleLocation(
                            locationResult.lastLocation.latitude,
                            locationResult.lastLocation.longitude
                        )


                        saveCurrentLocation(this@MapsActivity, current)

                    }
                },
                mainLooper
            )
        } else {
            callPermissions()
        }

    }

    private fun callPermissions() {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.FOREGROUND_SERVICE,
            Manifest.permission.ACCESS_BACKGROUND_LOCATION
        )
        Permissions.check(
            this,
            permissions,
            "location permission is required to use app",
            null,
            object : PermissionHandler() {
                override fun onGranted() {
                    requestLocationUpdates()
                }

            })
    }


    private var v: Float = 0.toFloat()
    fun animateCarOnMap(prev: LatLng, latest: LatLng) {
        innerMarker?.position = prev
        val valueAnimator = ValueAnimator.ofFloat(0f, 1f)
        valueAnimator.duration = 2000
        valueAnimator.interpolator = LinearInterpolator()
        valueAnimator.addUpdateListener {
            v = valueAnimator.animatedFraction
            val lng = v * latest.longitude + (1 - v) * prev.longitude
            val lat = v * latest.latitude + (1 - v) * prev.latitude
            val newPos = LatLng(lat, lng)
            innerMarker?.position = newPos
            innerMarker?.setAnchor(0.5f, 0.5f)
            innerMarker?.rotation = getBearing(prev, newPos)
            mGoogleMap?.animateCamera(
                CameraUpdateFactory.newCameraPosition
                    (CameraPosition.Builder().target(newPos).zoom(15.5f).build())
            )
        }
        valueAnimator.start()
    }


    override fun toggleRideOption(selection: Int) {
        binding.rideOption = selection
        when (selection) {
            1 -> {
                toggle()
                locationBox.visibility = View.VISIBLE
                mapsToolbar.visibility = View.VISIBLE
            }
            2 -> {
                toggle()
                pickLocationBox.visibility = View.VISIBLE
                mapsToolbar.visibility = View.VISIBLE
            }

            else -> {
                toggle()
                pickLocationBox.visibility = View.VISIBLE
                mapsToolbar.visibility = View.VISIBLE
            }
        }
    }


    fun openPlacePredictionCurrent(view: View) {
        val intent = Intent(this, PlacePredictionActivity::class.java)
        intent.putExtra(ARG_IS_SOURCE, true)
        startActivityForResult(intent, PLACE_REQUEST_CODE)
    }

    fun openPlacePredictionDestination(view: View) {
        val intent = Intent(this, PlacePredictionActivity::class.java)
        intent.putExtra(ARG_IS_SOURCE, false)
        startActivityForResult(intent, PLACE_REQUEST_CODE)
    }

    private fun toggle() {
        rental.visibility = View.GONE
        pickLocationBox.visibility = View.GONE
        locationBox.visibility = View.GONE
        ceremonyLayout.visibility = View.GONE
        mapsToolbar.visibility = View.GONE
        dialog_choose_vehicle.visibility = View.GONE
        dialog_driver_found.visibility = View.GONE
        dialog_vehicle_details.visibility = View.GONE
        dialog_ride_started.visibility = View.GONE
        dialog_searching_driver.visibility = View.GONE
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navHomeLayout.setBackgroundResource(0)

    }

}


interface RideOptionsInterface {
    fun toggleRideOption(selection: Int)
}

interface HomeInterface {
    fun openDrawer()

}
