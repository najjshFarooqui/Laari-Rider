package com.laari.rider.utility

import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng

fun Context.toast(message: CharSequence) =
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()

