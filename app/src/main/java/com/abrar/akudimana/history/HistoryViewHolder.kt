package com.abrar.akudimana.history

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.abrar.akudimana.R
import com.abrar.akudimana.database.LocationModel
import kotlinx.android.synthetic.main.item_history.view.*

class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindItem(location: LocationModel?) {
        with(itemView) {
            text_latitude.text = resources.getString(
                R.string.latitude,
                location?.latitude
            )
            text_longitude.text = resources.getString(
                R.string.longitude,
                location?.longitude
            )
            text_city.text = resources.getString(
                R.string.city,
                location?.city
            )
            text_country.text = resources.getString(
                R.string.city,
                location?.country
            )
        }
    }
}