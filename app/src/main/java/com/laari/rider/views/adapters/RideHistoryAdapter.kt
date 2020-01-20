package com.laari.rider.views.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.R
import com.laari.rider.RidesModel
import com.laari.rider.utility.*
import com.laari.rider.views.activity.RideDetailsActivity
import kotlinx.android.synthetic.main.adapter_ride_history.view.*

class RideHistoryAdapter(val model: ArrayList<RidesModel>) :
    RecyclerView.Adapter<RideHistoryAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_ride_history, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(model[position])
    }


    override fun getItemCount(): Int {
        return model.size
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(history: RidesModel) {

            itemView.tvPickup.text = history.fromLocation
            itemView.tvDrop.text = history.toLocation
            itemView.tvTime.text = history.date


            itemView.setOnClickListener {
                val history = model[adapterPosition]
                val intent =
                    Intent(itemView.context, RideDetailsActivity::class.java)

                intent.putExtra(PICKUP, history.fromLocation)
                intent.putExtra(DESTINATION, history.toLocation)
                intent.putExtra(TIME, history.date)
                intent.putExtra(ETA, history.eta)
                intent.putExtra(DISTANCE, history.distance)
                intent.putExtra(FARE, history.fare)
                intent.putExtra(WAITING_TIME, history.waitingTime)
                intent.putExtra(DATE, history.date)

                itemView.context.startActivity(intent)
            }

        }
    }
}