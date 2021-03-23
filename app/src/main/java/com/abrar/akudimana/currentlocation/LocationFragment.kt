package com.abrar.akudimana.currentlocation

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.abrar.akudimana.R
import com.abrar.akudimana.database.LocationDatabase
import com.abrar.akudimana.database.LocationModel
import com.abrar.akudimana.ViewModel
import com.google.android.gms.location.*
import kotlinx.android.synthetic.main.fragment_location.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class LocationFragment : Fragment() {

    private val viewModel: ViewModel by viewModel()

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    //unique id
    var PERMISSION_ID = 34

    private var locationDatabase: LocationDatabase? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity?)?.supportActionBar?.title = "Halaman - Aku Dimana?"

        //initiate fused lprovider client
        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireContext())

        //add event to button
        button_check_location.setOnClickListener {
            getLastLocation()
        }

    }

    //allow to get the last Location
    private fun getLastLocation() {

        if (checkSPermission()) {

            if (isLocationEnabled()) {
                fusedLocationProviderClient.lastLocation.addOnCompleteListener { task ->

                    val location: Location? = task.result
                    if (location == null) {
                        getNewLocation()

                    } else {
                        viewModel.insertLocation(LocationModel(
                            latitude = task.result.latitude.toString(),
                            longitude = task.result.longitude.toString(),
                            getCityName(task.result.latitude, task.result.longitude),
                            getCountryName(task.result.latitude, task.result.longitude)
                        ))
                        text_latitude.text =
                            resources.getString(R.string.latitude, task.result.latitude.toString())
                        text_longitude.text = resources.getString(
                            R.string.longitude,
                            task.result.longitude.toString()
                        )
                        text_city.text = resources.getString(
                            R.string.city,
                            getCityName(task.result.latitude, task.result.longitude)
                        )
                        text_country.text = resources.getString(
                            R.string.city,
                            getCountryName(task.result.latitude, task.result.longitude)
                        )
                        viewModel.getLocationList()
                        viewModel.liveHistory()
                    }

                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Nyalakan Location Service Anda!",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } else {
            requestPermission()
        }

    }

    private fun getNewLocation() {
        locationRequest = LocationRequest()
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest.interval = 0
        locationRequest.fastestInterval = 0
        locationRequest.numUpdates = 2
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {

            text_latitude.text =
                resources.getString(R.string.latitude, p0?.lastLocation?.latitude.toString())
            text_longitude.text =
                resources.getString(R.string.longitude, p0?.lastLocation?.longitude.toString())
        }
    }


    //check permission
    private fun checkSPermission(): Boolean {
        if (
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED ||
            ActivityCompat.checkSelfPermission(
                requireContext(),
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            return true
        }
        return false
    }

    //func to allow get user permission
    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            PERMISSION_ID
        )
    }

    private fun getCityName(lat: Double?, long: Double?): String {
        var cityName = ""
        var geoCoder = Geocoder(requireContext(), Locale.getDefault())

        var address = geoCoder.getFromLocation(lat!!, long!!, 1)
        cityName = address.get(0).locality
        return cityName
    }

    private fun getCountryName(lat: Double?, long: Double?): String {
        var countryName = ""
        var geoCoder = Geocoder(requireContext(), Locale.getDefault())

        var address = geoCoder.getFromLocation(lat!!, long!!, 1)
        countryName = address.get(0).countryName
        return countryName
    }

    //check Location service of the device is enabled
    private fun isLocationEnabled(): Boolean {
        val locationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_ID) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.d("permission log", "permission is granted")
            }
        }
    }


}