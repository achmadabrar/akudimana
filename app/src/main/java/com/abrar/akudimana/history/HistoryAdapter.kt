package com.abrar.akudimana.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.abrar.akudimana.R
import com.abrar.akudimana.database.LocationModel
import com.abrar.akudimana.database.LocationTable

class HistoryAdapter( val locationList: List<LocationModel>?
) : RecyclerView.Adapter<HistoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        return HistoryViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_history, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        locationList!![position].let {
            holder.bindItem(it)
        }
    }

    override fun getItemCount(): Int {
        return locationList!!.size
    }
}