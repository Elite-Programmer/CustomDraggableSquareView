package com.example.nikidemoapp.ui.squaresList.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.nikidemoapp.ui.customViews.CustomDrawerView
import com.example.nikidemoapp.R
import com.example.nikidemoapp.model.SquareData

class SquaresListAdapter constructor(val list: List<SquareData>) :
    RecyclerView.Adapter<SquaresListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.listitem_squares_data, parent, false)
        return ViewHolder(view)

    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listItem = list[position]
        val serialNo = position + 1
        holder.tvSerialNo.setText("$serialNo")
        holder.tvCoordinates.setText(CustomDrawerView.getCoordinates(listItem.center, listItem.length))
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        var tvSerialNo: TextView
        var tvCoordinates: TextView

        init {
            tvSerialNo = v.findViewById(R.id.tv_serial_no)
            tvCoordinates = v.findViewById(R.id.tv_coordinates)
        }

    }
}