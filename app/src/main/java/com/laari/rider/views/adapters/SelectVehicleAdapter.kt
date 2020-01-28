package com.laari.rider.views.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat

import androidx.recyclerview.widget.RecyclerView

import com.laari.rider.R
import com.laari.rider.models.AvailableVehicleModel
import kotlinx.android.synthetic.main.vehicle_list_layout.view.*


internal class SelectVehicleAdapter(
    var context: Context,
    var model: ArrayList<AvailableVehicleModel>
) :
    RecyclerView.Adapter<SelectVehicleAdapter.MyViewHolder>() {
    var index = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.vehicle_list_layout, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItems(model[position])
        holder.itemView.vehicleListLayout.setOnClickListener(View.OnClickListener {
            index = position
            notifyDataSetChanged()
        })

        if (index == position) {
            holder.itemView.vehicleListLayout.background =
                ContextCompat.getDrawable(context, R.drawable.bg_selected_round)
            holder.itemView.tvRideFare.setTextColor(Color.parseColor("#52A9F5"));
            holder.itemView.tvVehicleName.setTextColor(Color.parseColor("#52A9F5"));
            holder.itemView.tvPessanjors.setTextColor(Color.parseColor("#52A9F5"));

        } else {
            holder.itemView.vehicleListLayout.background =
                ContextCompat.getDrawable(context, R.drawable.bg_unselected_round)
            holder.itemView.tvRideFare.setTextColor(Color.parseColor("#000000"));
            holder.itemView.tvVehicleName.setTextColor(Color.parseColor("#000000"));
            holder.itemView.tvPessanjors.setTextColor(Color.parseColor("#000000"));
        }
    }

    override fun getItemCount(): Int {
        return model.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(model: AvailableVehicleModel) {

            itemView.tvPessanjors.text = model.capacity
            itemView.tvRideFare.text = model.fare
            //Todo: select proper image
            itemView.tvVehicleName.text = model.name

        }

    }

}
