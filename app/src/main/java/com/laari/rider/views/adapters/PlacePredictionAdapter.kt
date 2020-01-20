package com.laari.rider.views.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.R
import com.laari.rider.models.PlacePrediction
import com.laari.rider.views.activity.PlacePredictionInterface
import kotlinx.android.synthetic.main.item_place_predection.view.*


class PlacePredictionAdapter(val handler: PlacePredictionInterface) : RecyclerView.Adapter<PlacePredictionAdapter.PlacePredictionViewHolder>() {

    var list:List<PlacePrediction> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlacePredictionViewHolder {
        return PlacePredictionViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_place_predection, parent, false))
    }

    fun notifyDataSetChanged(list:List<PlacePrediction>){
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: PlacePredictionViewHolder, position: Int) {
        holder.bindTo(list[position])
    }

    inner class PlacePredictionViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bindTo(place: PlacePrediction){
            itemView.primaryLabel.text = place.primaryText
            itemView.secondaryLabel.text = place.secondaryText
            itemView.setOnClickListener {
                handler.onPlaceSelected(place)
            }
        }
    }

}