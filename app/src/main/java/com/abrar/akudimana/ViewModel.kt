package com.abrar.akudimana

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abrar.akudimana.database.LocationInterface
import com.abrar.akudimana.database.LocationModel
import com.abrar.akudimana.domain.repository.LocationRepository
import kotlinx.coroutines.launch

class ViewModel constructor(
    private val locationRepository: LocationRepository
) : ViewModel() {

    var listHistory = MutableLiveData<List<LocationModel>>()
    var history: List<LocationModel>? = null

    fun insertLocation(locationModel: LocationModel) {
        viewModelScope.launch {
            locationRepository.saveLocation(locationModel)
        }
    }

    fun getLocationList(): List<LocationModel>? {
        viewModelScope.launch {
            locationRepository.getLocation(object : LocationInterface{
                override fun onSuccessSave(locationModel: List<LocationModel>?) {
                    history = locationModel
                }

            })
        }

        return history
    }

    fun liveHistory() {
        if (!history.isNullOrEmpty()) {
            listHistory.value = history
            listHistory
        }
    }
}