package com.laari.rider.utility

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.laari.rider.models.SimpleLocation


fun setLoggedIn(context: Context, loggedIn: Boolean) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putBoolean("login_flag", loggedIn).apply()
}

fun isLoggedIn(context: Context): Boolean {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getBoolean("login_flag", false)
}

fun setFullName(context: Context, fullName: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("full_name", fullName).apply()
}

fun getFullName(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("full_name", "")
}

fun setPhoneNo(context: Context, phoneNo: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("phone_no", phoneNo).apply()
}

fun getPhoneNo(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("phone_no", "")
}

fun setEmail(context: Context, email: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("email", email).apply()
}

fun getEmail(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("email", "")
}


fun setProfilePhoto(context: Context, photo: String?) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("profile_photo", photo).apply()
}

fun getProfilePhoto(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("profile_photo", "")
}


fun setGender(context: Context, gender: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("gender", gender).apply()
}

fun getGender(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("gender", "")
}


fun setUserName(context: Context, userName: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userName", userName).apply()
}

fun getUserName(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userName", "")
}


fun setUserId(context: Context, userId: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("id", "").apply()
}

fun getUserId(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("id", "")
}


fun setAddress(context: Context, address: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("address", address).apply()
}

fun getAddress(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("address", "")
}


fun setToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("token", token).apply()
}

fun getToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("token", "")
}


fun setPasswordResetToken(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("passwordResetToken", token).apply()
}

fun getPasswordResetToken(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("passwordResetToken", "")
}


fun setUserKey(context: Context, token: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("userKey", token).apply()
}

fun getUserKey(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("userKey", "")
}


fun setCountryCode(context: Context, code: String) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.putString("countryCode", code).apply()

}


fun getCountryCode(context: Context): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString("countryCode", "")
}


fun save(context: Context, key: String, value: String?) {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    sharedPreferences.edit().apply { putString(key, value) }.apply()
}

fun saveCurrentLocation(context: Context, location: SimpleLocation?) {
    save(context, CURRENT_LOCATION, location?.let { Gson().toJson(it) })
}

fun get(context: Context, key: String, default: String?): String? {
    val sharedPreferences = context.getSharedPreferences("laariRider", Context.MODE_PRIVATE)
    return sharedPreferences.getString(key, default)
}


fun getCurrentLocation(context: Context): SimpleLocation? {
    val savedLocation = get(context, CURRENT_LOCATION, null)
    return savedLocation?.let {
        try {
            Gson().fromJson(it, SimpleLocation::class.java)
        } catch (e: Exception) {
            null
        }
    }
}






