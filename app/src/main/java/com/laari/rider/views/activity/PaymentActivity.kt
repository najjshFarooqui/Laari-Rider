package com.laari.rider.views.activity

import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.RadioButton
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R
import com.laari.rider.databinding.ActivityPaymentBinding
import com.laari.rider.utility.toast
import kotlinx.android.synthetic.main.activity_payment.*
import kotlinx.android.synthetic.main.nav_header.*
import kotlinx.android.synthetic.main.toolbar.*


class PaymentActivity : HomeBaseActivity(),
    HomeInterface {
    private lateinit var binding: ActivityPaymentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_payment)
        binding.baseHandler = this
        binding.handler = this
        navPaymentLayout.background =
            ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
        ivBack.setOnClickListener {
            drawer_layout.openDrawer(Gravity.LEFT)
        }





    }

    override fun openDrawer() {
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    override fun onResume() {
        super.onResume()
        navPaymentLayout.background =
            ResourcesCompat.getDrawable(getResources(), R.mipmap.side_menu_selected, null)
    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                R.id.cash ->
                    if (checked) {
                        toast("cash")
                    }
                R.id.slip ->
                    if (checked) {
                        toast("card")
                    }

                R.id.wallet ->
                    if (checked) {
                        toast("wallet")
                    }
                R.id.telenor ->
                    if (checked) {
                        toast("telenor")
                    }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()

        navPaymentLayout.setBackgroundResource(0)

    }

}
