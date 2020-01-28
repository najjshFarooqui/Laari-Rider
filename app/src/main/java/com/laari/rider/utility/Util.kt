package com.laari.rider.utility

import android.app.ActionBar
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager


import android.text.TextUtils
import android.util.Patterns
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.laari.rider.R
import java.io.IOException
import java.util.*


fun Context.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
    val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
    vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
    val bitmap =
        Bitmap.createBitmap(
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
    val canvas = Canvas(bitmap)
    vectorDrawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bitmap)
}


fun ActionBar.setUp(title: String) {
    this.title = title
    this.setHomeAsUpIndicator(R.drawable.ic_arrow_back)
    this.setDisplayShowTitleEnabled(true)
    this.setDisplayHomeAsUpEnabled(true)
}

fun String.isValidName(): Boolean {
    if (this.isEmpty()) return false
    return this.matches("""[a-zA-Z ]+""".toRegex())
}

fun String.isValidPassword(): Boolean {
    if (this.isEmpty()) return false
    return this.matches("""[0-9]+""".toRegex())
}

fun String.isValidEmail() =
    !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun isNetworkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetworkInfo = connectivityManager.activeNetworkInfo
    return activeNetworkInfo != null
}


fun Context.getAddress(lat: Double, lng: Double): String {
    val geocoder = Geocoder(this, Locale.getDefault())
    return try {
        val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
        val obj: Address = addresses.get(0)
        val add = obj.getAddressLine(0)
        add
    } catch (e: IOException) {
        ""
    }
}


fun Context.getDirectionsUrl(origin: LatLng, dest: LatLng): String {
    val str_origin = "origin=" + origin.latitude + "," + origin.longitude
    val str_dest = "destination=" + dest.latitude + "," + dest.longitude
    val sensor = "sensor=false"
    var placesApi = "key=" + getString(R.string.map_api_key)
    val parameters = "$str_origin&$str_dest&$sensor&$placesApi"
    return "https://maps.googleapis.com/maps/api/directions/json?$parameters"
}



fun getProgressDialog(activity: AppCompatActivity): ProgressDialog {
    val progressDialog = ProgressDialog(activity)
    progressDialog.setCancelable(false)
    progressDialog.setOnKeyListener(DialogInterface.OnKeyListener { dialog, keyCode, _ ->
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialog.dismiss()
            activity.finish()
            return@OnKeyListener true
        }
        false
    })
    return progressDialog
}