package com.laari.rider.views.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.laari.rider.PromosModel
import com.laari.rider.R

class PromosAdapter(val model: ArrayList<PromosModel>) :
    RecyclerView.Adapter<PromosAdapter.ViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.promos_adapter, parent, false)
        return ViewHolder(v)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(model[position])
    }


    override fun getItemCount(): Int {
        return model.size
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindItems(promos: PromosModel) {
            val promoId = itemView.findViewById(R.id.tvPromo) as TextView
            val promoValidity = itemView.findViewById(R.id.tvValidity) as TextView

            promoId.text = promos.promoId
            promoValidity.text = promos.promoValidity


        }
    }
}