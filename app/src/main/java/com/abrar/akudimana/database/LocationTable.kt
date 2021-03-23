package com.abrar.akudimana.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters

@TypeConverters(ListConverter::class)
data class LocationTable (
    @PrimaryKey val location: List<LocationModel>
)