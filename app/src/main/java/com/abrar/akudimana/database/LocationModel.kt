package com.abrar.akudimana.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_location")
data class LocationModel (
    @PrimaryKey val latitude: String,
    val longitude: String,
    val city: String,
    val country: String
)