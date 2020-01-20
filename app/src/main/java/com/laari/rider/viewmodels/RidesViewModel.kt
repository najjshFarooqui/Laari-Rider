package com.laari.rider.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.laari.rider.RidesModel
import com.laari.rider.ScheduleRidesModel


class RidesViewModel(val context: Application) : AndroidViewModel(context) {
    var rideHistoryList: MutableLiveData<ArrayList<RidesModel>>? = null
    var scheduleRidesList : MutableLiveData<ArrayList<ScheduleRidesModel>>? = null



    fun getScheduledRides(): LiveData<ArrayList<ScheduleRidesModel>>{
        if(scheduleRidesList== null){
            scheduleRidesList = MutableLiveData()
            loadScheduledRides()
        }
        return scheduleRidesList!!

    }

    fun getRideHistory(): LiveData<ArrayList<RidesModel>> {
        if (rideHistoryList == null) {
            rideHistoryList = MutableLiveData()
            loadRideHistory()
        }
        return rideHistoryList!!
    }

    private fun loadRideHistory() {
        val history = ArrayList<RidesModel>()
        history.add(
            RidesModel(
                "indore rajwada",
                "indore vijay nagar",
                " Friday 12:40 pm",
                "10 min",
                "2.3 km",
                "200rs",
                "5-10 min",
                "15 min"

            )
        )
        history.add(
            RidesModel(
                "Mumbai",
                "Delhi Noida",
                " Friday 12:40 pm",
                "5 min",
                "2.3 km",
                "200rs",
                "5-10 min",
                "10 min"
            )
        )



        history.add(
            RidesModel(
                "Mahidpur",
                "Ujjain",
                "Friday 12:40 pm",
                "3 min",
                "6.3 km",
                "400rs",
                "5-10 min",
                "30 min"

            )
        )

        rideHistoryList!!.value = history
    }






    private fun loadScheduledRides(){
        val schedules = ArrayList<ScheduleRidesModel>()

        schedules.add(
            ScheduleRidesModel(
                "indore rajwada",
                "indore vijay nagar",
                "Friday Aug 19 - 08:00 PM",
                " Daily Ride",
                "15 min",
                "4 KM",
                "120 RS"
            )
        )
        schedules.add(
            ScheduleRidesModel(
                "Navi Mumbai",
                "Bandra",
                "Friday Aug 19 - 08:00 PM",
                " Daily Ride",
                "15 min",
                "8 KM",
                "240 RS"
            )
        )
        scheduleRidesList!!.value = schedules
    }
}