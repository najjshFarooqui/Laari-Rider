package com.laari.rider.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class SimpleLocation(var latitude: Double, var longitude: Double): Parcelable