package com.abrar.akudimana.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocationModel::class],
    version = 2,
    exportSchema = true
)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

}