package com.laari.rider.utility

import android.location.Location
import com.google.android.gms.maps.model.LatLng



fun getDistance(source: LatLng, dest: LatLng): Float {

    val lat_a = source.latitude
    val lng_a = source.longitude
    val lat_b = dest.latitude
    val lng_b = dest.longitude

    val earthRadius = 3958.75
    val latDiff = Math.toRadians((lat_b - lat_a).toDouble())
    val lngDiff = Math.toRadians((lng_b - lng_a).toDouble())
    val a =
            Math.sin(latDiff / 2) * Math.sin(latDiff / 2) + Math.cos(Math.toRadians(lat_a.toDouble())) * Math.cos(
                    Math.toRadians(lat_b.toDouble())
            ) *
                    Math.sin(lngDiff / 2) * Math.sin(lngDiff / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    val distance = earthRadius * c

    val meterConversion = 1609

    return (distance * meterConversion).toFloat()
}


fun getBearing(begin: LatLng, end: LatLng): Float {
    val lat = Math.abs(begin.latitude - end.latitude);
    val lng = Math.abs(begin.longitude - end.longitude);

    if (begin.latitude < end.latitude && begin.longitude < end.longitude)
        return (Math.toDegrees(Math.atan(lng / lat))).toFloat()
    else if (begin.latitude >= end.latitude && begin.longitude < end.longitude)
        return ((90 - Math.toDegrees(Math.atan(lng / lat))) + 90).toFloat()
    else if (begin.latitude >= end.latitude && begin.longitude >= end.longitude)
        return (Math.toDegrees(Math.atan(lng / lat)) + 180).toFloat()
    else if (begin.latitude < end.latitude && begin.longitude >= end.longitude)
        return ((90 - Math.toDegrees(Math.atan(lng / lat))) + 270).toFloat()
    return -1F;
}