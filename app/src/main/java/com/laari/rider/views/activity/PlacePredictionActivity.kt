package com.laari.rider.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.laari.rider.R
import com.laari.rider.databinding.ActivityPlacePredictionBinding
import com.laari.rider.models.PlacePrediction
import com.laari.rider.utility.*
import com.laari.rider.views.adapters.PlacePredictionAdapter
import kotlinx.android.synthetic.main.activity_place_prediction.*
import java.util.*

class PlacePredictionActivity : AppCompatActivity(), PlacePredictionInterface {
    lateinit var placesClient: PlacesClient
    lateinit var adapter: PlacePredictionAdapter
    lateinit var binding: ActivityPlacePredictionBinding
    private var isSource = true


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_place_prediction)
        binding.handler = this

        setSupportActionBar(toolbar)
        isSource = intent.getBooleanExtra(ARG_IS_SOURCE, true)

        if (isSource) {
            supportActionBar?.title = "Enter Pickup Location"
            ivPin.setBackground(ContextCompat.getDrawable(this, R.drawable.ic_location_pin_orange));
        } else supportActionBar?.title = "Enter Destination Location"





        Places.initialize(applicationContext, getString(R.string.map_api_key))
        placesClient = Places.createClient(this)
        adapter = PlacePredictionAdapter(this)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onPlaceChanged(text: CharSequence) {

        val token = AutocompleteSessionToken.newInstance()
        val request = FindAutocompletePredictionsRequest.builder()
            .setQuery(text.toString())
            .setSessionToken(token)
            //TODO: change country code
            .setCountry("in")
            .build()

        val task = placesClient.findAutocompletePredictions(request)

        task.addOnSuccessListener {
            val placePredictions: ArrayList<PlacePrediction> = arrayListOf()
            it.autocompletePredictions.forEach {
                val primaryText = it.getPrimaryText(null).toString()
                val secondaryText = it.getSecondaryText(null).toString()
                val fullText = it.getFullText(null).toString()
                placePredictions.add(
                    PlacePrediction(
                        it.placeId,
                        primaryText,
                        secondaryText,
                        fullText
                    )
                )
            }
            adapter.notifyDataSetChanged(placePredictions)
        }

        task.addOnFailureListener {
            Log.d("abc123", it.message)
        }
    }

    override fun onPlaceSelected(place: PlacePrediction) {
        //  showProgress()
        val placeFields = Arrays.asList(Place.Field.ID, Place.Field.NAME, Place.Field.LAT_LNG)
        val request = FetchPlaceRequest.builder(place.placeId, placeFields).build()
        val placesClient = Places.createClient(this)
        placesClient.fetchPlace(request).addOnSuccessListener {

            //  hideProgress()
            val intent = Intent()
            intent.putExtra(ARG_LATITUDE, it.place.latLng?.latitude)
            intent.putExtra(ARG_LONGITUDE, it.place.latLng?.longitude)
            intent.putExtra(ARG_FULL_LOCATION, place.fullText)
            intent.putExtra(ARG_PLACE_ID, place.placeId)
            intent.putExtra(ARG_IS_SOURCE, isSource)
            setResult(PLACE_REQUEST_CODE, intent)
            finish()


        }.addOnFailureListener {
            Log.d("abc123", it.message)
        }

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }
}

interface PlacePredictionInterface {
    fun onPlaceChanged(text: CharSequence)

    fun onPlaceSelected(place: PlacePrediction)
}