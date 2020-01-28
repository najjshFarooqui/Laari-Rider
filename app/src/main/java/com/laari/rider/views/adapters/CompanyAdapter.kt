package com.laari.rider.views.adapters

import com.laari.rider.network.auth.Company

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.laari.rider.R


class CompanyAdapter(val context: Context, var companyList: ArrayList<Company>) : BaseAdapter() {
    val mInflater: LayoutInflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val vh: ItemRowHolder
        if (convertView == null) {
            view = mInflater.inflate(R.layout.spinner_view, parent, false)
            vh = ItemRowHolder(view)
            view?.tag = vh
        } else {
            view = convertView
            vh = view.tag as ItemRowHolder
        }
        vh.label.text = companyList.get(position)!!.companyName
        return view
    }

    override fun getItem(position: Int): Any? {

        return null

    }

    override fun getItemId(position: Int): Long {

        return 0

    }

    override fun getCount(): Int {

        return companyList.size

    }

    private class ItemRowHolder(row: View?) {

        val label: TextView

        init {
            this.label = row?.findViewById(R.id.txtDropDownLabel) as TextView
        }
    }


}