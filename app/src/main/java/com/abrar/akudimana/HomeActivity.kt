package com.abrar.akudimana

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.abrar.akudimana.currentlocation.LocationFragment
import com.abrar.akudimana.history.HistoryFragment

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        //change fragment
        supportFragmentManager.beginTransaction().replace(R.id.frame_container, LocationFragment())
            .addToBackStack(null).commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.list_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.menu_location -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.frame_container,
                    LocationFragment()
                ).addToBackStack(null).commit()
                return true
            }
            R.id.menu_history -> {
                supportFragmentManager.beginTransaction().replace(
                    R.id.frame_container,
                    HistoryFragment()
                ).addToBackStack(null).commit()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}