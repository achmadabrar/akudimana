package com.abrar.akudimana.domain.repository

import android.util.Log
import com.abrar.akudimana.database.LocationDao
import com.abrar.akudimana.database.LocationInterface
import com.abrar.akudimana.database.LocationModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LocationRepository(private val locationDao: LocationDao) {

    suspend fun saveLocation(locationModel: LocationModel) {
        withContext(Dispatchers.IO) {
            locationDao.insertLocation(locationModel)
            val locationList = locationDao.getLocation()
            Log.d("locationYangDiSave", "location = $locationList")
        }
    }

    suspend fun getLocation(locationInterface: LocationInterface) {
        withContext(Dispatchers.IO) {
            val locationList = locationDao.getLocation()
            locationInterface.onSuccessSave(locationList)
        }
    }
}