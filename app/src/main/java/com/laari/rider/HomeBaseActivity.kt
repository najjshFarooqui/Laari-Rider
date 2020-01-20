package com.laari.rider

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Build
import android.provider.Settings
import android.util.Base64
import android.util.Log
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.laari.rider.utility.setLoggedIn
import com.laari.rider.views.activity.*
import com.laari.rider.views.activity.registration.PhoneAuthActivity
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.nav_header.*
import java.io.ByteArrayOutputStream
import java.security.MessageDigest
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Pattern

open class HomeBaseActivity : AppCompatActivity(), HomeBaseInterface {
    val TAG = "farooqu123"
    private lateinit var mSnackbar: Snackbar
    private var mAlertDialog: AlertDialog? = null


    override fun onHomeClicked() {
        setSelected()
        navHomeLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        val intent = Intent(this, HomeDashboardActivity::class.java)
        startActivity(intent)
        finish()
    }


    override fun onScheduleRidesClicked() {
        setSelected()
        navScheduleRidesLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        val intent = Intent(this, ScheduleRidesActivity::class.java)
        startActivity(intent)
    }

    override fun onRideNotificationClicked() {
        setSelected()
        navNotificationLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        startActivity(Intent(this, NotificationActivity::class.java))

    }


    override fun onReferralClicked() {
        setSelected()
        navReferralLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        startActivity(Intent(this, ReferFriendAcivity::class.java))

    }

    override fun onRideHistoryClicked() {
        setSelected()
        navRideHistoryLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        val intent = Intent(this, RideHistoryActivity::class.java)
        startActivity(intent)
    }

    override fun onPromosClicked() {
        setSelected()
        navPromosLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        val intent = Intent(this, PromosActivity::class.java)
        startActivity(intent)
    }

    override fun onPaymentClicked() {
        setSelected()
        navPaymentLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        val intent = Intent(this, PaymentActivity::class.java)
        startActivity(intent)

    }


    override fun onAboutUsClicked() {
        setSelected()
        val intent = Intent(this, AboutUsActivity::class.java)
        startActivity(intent)

    }


    override fun onLogoutClicked() {
        setSelected()
        navLogoutLayout.background =
            ResourcesCompat.getDrawable(
                resources,
                R.mipmap.side_menu_selected, null
            )
        FirebaseAuth.getInstance().signOut()
        setLoggedIn(this, false)
        clearAllActivitiesFromStack(PhoneAuthActivity())
    }





    open fun setSelected() {
        navHomeLayout.setBackgroundResource(0)
        navScheduleRidesLayout.setBackgroundResource(0)
        navNotificationLayout.setBackgroundResource(0)
        navReferralLayout.setBackgroundResource(0)
        navRideHistoryLayout.setBackgroundResource(0)
        navPromosLayout.setBackgroundResource(0)
        navPaymentLayout.setBackgroundResource(0)
        navAboutUsLayout.setBackgroundResource(0)
        navLogoutLayout.setBackgroundResource(0)

    }


    open fun setToolbar(title: String) {
        val toolbarTop = findViewById(R.id.main_toolbar) as Toolbar
        val mTitle = toolbarTop.findViewById(R.id.toolbarTitle) as TextView
        mTitle.text = title

    }


    fun printlog(tag: String, message: String) {
        Log.e(TAG, tag + "\n" + message)
    }


    fun isInternetConnected(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
        return if (connectivityManager is ConnectivityManager) {
            val networkInfo: NetworkInfo? = connectivityManager.activeNetworkInfo
            networkInfo?.isConnected ?: false
        } else false
    }


    fun clearAllActivitiesFromStack(activity: Activity) {
        /*  To use it
            clearAllActivitiesFromStack(new LoginActivity());
         */
        startActivity(
            Intent(
                this,
                activity.javaClass
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        )
        finish()
    }

    /*
       Clear all the activities from the stack
    */
    fun startNewActivity(activity: Activity) {
        startActivity(Intent(this, activity.javaClass))
    }


    @SuppressLint("HardwareIds")
    fun getDeviceID(): String {
        return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
    }

    fun checkImageResource(imageView: ImageView?, imageResource: Int): Boolean {
        var result = false
        if (imageView != null && imageView.drawable != null) {
            val constantState: Drawable.ConstantState?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                constantState = resources.getDrawable(imageResource, theme).constantState
            } else {
                constantState = resources.getDrawable(imageResource).constantState
            }
            if (imageView.drawable.constantState === constantState) {
                result = true
            }
        }
        return result
    }


    fun setActivityOnFullScreen() {
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestWindowFeature(Window.FEATURE_NO_TITLE)
    }


    fun getCurrentActivityName(): String {
        return this.javaClass.simpleName
    }

    /*
    Start the new activity with passing the Model class
     */
//    fun startNewActivityByPassingModelClass(activity:Activity,model: Model){
//        /*
//       Add this dependecny to pass the Model class
//           compile 'com.google.code.gson:gson:2.8.2'
//       How to use
//           startNewActivityByPassingModelClass(this,new LoginActivity(),Model)
//        */
//        startActivity(Intent(this,activity.javaClass).putExtra(Constants.MODEL,Gson().toJson(model)))
//        /*
//       //To get in second class
//        if (getIntent().getExtras() != null) {
//           Model mModel = new Gson().fromJson(getIntent().getExtras().getString(Constants.MODEL), new TypeToken<Model>() {}.getType());
//       }
//        */
//    }

    /*
    Method to show the Snackbar using Linearlayout
    */
    fun showSnackbar(linearLayout: View, message: String) {
        mSnackbar = Snackbar.make(linearLayout, message, Snackbar.LENGTH_LONG).setAction("") {
            @Override
            fun onClick(v: View) {
                mSnackbar.dismiss()
            }
        }
        val snackbarView = mSnackbar.view
        snackbarView.setBackgroundColor(
            ContextCompat.getColor(
                this,
                R.color.colorOrange
            )
        )
        val textview =
            snackbarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textview.setTextColor(
            ContextCompat.getColor(
                this,
                R.color.colorWhite
            )
        )
        textview.maxLines = 5
        mSnackbar.show()
    }

    /*
    method to convert the Imageview's image into the base64 format
     */
    fun getBase64FromImageview(imageView: ImageView): String {
        imageView.buildDrawingCache()
        val bmap = imageView.drawingCache

        val stream = ByteArrayOutputStream()
        bmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val byteFormat = stream.toByteArray()

        return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
    }


    fun getBase64ForLargeImage(imageView: ImageView): String {
        imageView.buildDrawingCache()
        val bmap = imageView.drawingCache

        val stream = ByteArrayOutputStream()
        bmap.compress(Bitmap.CompressFormat.JPEG, 90, stream)
        val byteFormat = stream.toByteArray()

        return Base64.encodeToString(byteFormat, Base64.NO_WRAP)
    }


    fun isEmailValid(email: String): Boolean {

        if (email.trim().isEmpty()) return false

        val expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$"
        val pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE)
        val matcher = pattern.matcher(email)
        return matcher.matches()
    }

    fun showDatePicker(textDate: TextView) {
        val calendar = Calendar.getInstance()
        val dialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { arg0, year, month, day_of_month ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, month)
                calendar.set(Calendar.DAY_OF_MONTH, day_of_month)
                val myFormat = "dd MMM hh:mm"
                val sdf = SimpleDateFormat(myFormat, Locale.getDefault())
                var date = sdf.format(
                    calendar.time
                ) + showTimePicker()
                textDate.text = date
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        dialog.datePicker.minDate =
            calendar.timeInMillis// TODO: used to hide previous date,month and year calendar.add(Calendar.YEAR, 0)
        //       dialog.datePicker.maxDate =
        //           calendar.timeInMillis// TODO: used to hide future date,month and year
        dialog.show()
    }


    private fun showTimePicker(): String {
        // Get Current Time
        val c = Calendar.getInstance()
        var mHour = c.get(Calendar.HOUR_OF_DAY)
        var mMinute = c.get(Calendar.MINUTE)
        var time: String = ""

        // Launch Time Picker Dialog
        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                mHour = hourOfDay
                mMinute = minute

                time = " $hourOfDay:$minute"
            }, mHour, mMinute, false
        )
        timePickerDialog.show()
        return time
    }

    fun getLetitude(): String {
        return "30.75"
    }

    fun getLongitude(): String {
        return "76.74"
    }

    fun generateSSHKey(context: Context) {
        try {
            val info = context.packageManager.getPackageInfo(
                context.packageName,
                PackageManager.GET_SIGNATURES
            )
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                val hashKey = String(Base64.encode(md.digest(), 0))
                Log.e("tag", "key:$hashKey=")
            }
        } catch (e: Exception) {
            Log.e("tag", "error:", e)
        }

    }

    fun shareText(subject: String, body: String) {
        val txtIntent = Intent(android.content.Intent.ACTION_SEND)
        txtIntent.type = "text/plain"
        txtIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, subject)
        txtIntent.putExtra(android.content.Intent.EXTRA_TEXT, body)
        startActivity(Intent.createChooser(txtIntent, "Share"))
    }


    fun showProgress() {
        dismissProgress()
        if (mAlertDialog == null) {
            mAlertDialog = SpotsDialog.Builder()
                .setContext(this)
                .setTheme(R.style.CustomDialog)
                .build()
        }
        mAlertDialog?.show()
    }

    fun dismissProgress() {
        if (mAlertDialog != null && mAlertDialog!!.isShowing) {
            mAlertDialog!!.dismiss()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

    }
}


interface HomeBaseInterface {
    fun onHomeClicked()
    fun onScheduleRidesClicked()
    fun onRideNotificationClicked()
    fun onReferralClicked()
    fun onRideHistoryClicked()
    fun onPromosClicked()
    fun onPaymentClicked()
    fun onAboutUsClicked()
    fun onLogoutClicked()


}



