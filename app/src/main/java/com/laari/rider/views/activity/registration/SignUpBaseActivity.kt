package com.laari.rider.views.activity.registration

import android.os.Bundle
import android.os.PersistableBundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.laari.rider.HomeBaseActivity
import com.laari.rider.R



open class SignUpBaseActivity : HomeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_photo)




    }








    private fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        val manager = supportFragmentManager
        val ft = manager.beginTransaction()

        if (addToBackStack) {
            ft.addToBackStack(tag)
        }
        ft.replace(R.id.container_frame, fragment, tag)
        ft.commitAllowingStateLoss()
    }
}

