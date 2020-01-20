package com.laari.rider.views.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.R

import com.laari.rider.ScheduleRidesModel
import com.laari.rider.utility.*
import com.laari.rider.views.activity.RideDetailsActivity
import com.laari.rider.views.activity.ScheduledRideDetailsActivity
import kotlinx.android.synthetic.main.adapter_ride_history.view.*

class ScheduleRidesAdapter(val list: ArrayList<ScheduleRidesModel>) :
    RecyclerView.Adapter<ScheduleRidesAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_ride_history, parent, false)
        return ViewHolder(
            v
        )
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(list[position])
    }


    override fun getItemCount(): Int {
        return list.size
    }


   inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(schedules: ScheduleRidesModel) {


            itemView.tvTime.text = schedules.time
            itemView.tvDrop.text = schedules.toLocation
            itemView.tvPickup.text = schedules.fromLocation




            itemView.setOnClickListener {
                val list = list[adapterPosition]
                val intent =
                    Intent(itemView.context, ScheduledRideDetailsActivity::class.java)

                intent.putExtra(PICKUP, list.fromLocation)
                intent.putExtra(DESTINATION, list.toLocation)
                intent.putExtra(TIME, list.time)
                intent.putExtra(ETA, list.eta)
                intent.putExtra(DISTANCE, list.distance)
                intent.putExtra(FARE, list.fare)
                intent.putExtra("rideType",list.rideType)
                itemView.context.startActivity(intent)
            }

        }
    }
}