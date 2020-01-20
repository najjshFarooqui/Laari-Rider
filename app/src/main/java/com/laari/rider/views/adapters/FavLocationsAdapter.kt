package com.laari.rider.views.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat.startActivityForResult

import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.R

import com.laari.rider.models.FavLocationsModel
import com.laari.rider.utility.PLACE_REQUEST_CODE
import com.laari.rider.views.activity.HomeDashboardActivity
import com.laari.rider.views.activity.PlacePredictionActivity
import kotlinx.android.synthetic.main.rv_fav_inside_adapter.view.*

class FavLocationsAdapter(var list: List<FavLocationsModel>, val context: Context) :
    RecyclerView.Adapter<FavLocationsAdapter.RideViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        return RideViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.rv_fav_inside_adapter,
                parent,
                false
            )
        )
    }

    fun notifyDataSetChanged(list: List<FavLocationsModel>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    inner class RideViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(model: FavLocationsModel) {


            itemView.tvFav.text = model.name
            // intent.putExtra("distance", details.distance)
            // intent.putExtra("earned", details.earned)
            // intent.putExtra("eta", details.eta)
            when {
                itemView.tvFav.text.equals("Office") -> itemView.favIv.setImageResource(R.mipmap.office)
                itemView.tvFav.text.equals("Home") -> itemView.favIv.setImageResource(R.mipmap.home)
                itemView.tvFav.text.equals("Hospital") -> itemView.favIv.setImageResource(R.mipmap.hospital)
            }


          //itemView.setOnClickListener {
          //    val details = list.get(adapterPosition)
          //    Toast.makeText(context, details.name, Toast.LENGTH_SHORT).show()
          //    val intent = Intent(itemView.context, PlacePredictionActivity::class.java)

          //    intent.putExtra("destination", details.name)

          //    // intent.putExtra("distance", details.distance)
          //    // intent.putExtra("earned", details.earned)
          //    // intent.putExtra("eta", details.eta)

          //    itemView.context.startActivity(intent)
          //}
        }
    }

}


