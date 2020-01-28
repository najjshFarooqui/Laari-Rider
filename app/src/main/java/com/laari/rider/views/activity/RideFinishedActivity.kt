package com.laari.rider.views.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.utility.toast
import kotlinx.android.synthetic.main.activity_ride_finished.*

class RideFinishedActivity : HomeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ride_finished)

        btnSubmitFeedback.setOnClickListener {
            startActivity(
                Intent(
                    this,
                    HomeDashboardActivity::class.java
                ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            )
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        startActivity(
            Intent(
                this,
                HomeDashboardActivity::class.java
            ).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        )
    }
}
