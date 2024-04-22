/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.session2.common.APIkey
import com.example.session2.view.YandexMapFragment
import com.example.session2.viewmodel.ProfileViewModel
import com.example.session2.viewmodel.StateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), UserLocationObjectListener {
    private lateinit var stateViewModel: StateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(APIkey.MAPKIT_API_KEY)
        setContentView(R.layout.activity_main)
        stateViewModel = ViewModelProvider(this)[StateViewModel::class.java]


        val bottomNav =findViewById<BottomNavigationView>(R.id.bottomnav)
        val nameCards = findViewById<TextView>(R.id.name_window)
        val cwindowCard = findViewById<CardView>(R.id.cwindowCard)
        val arrowback = findViewById<ImageView>(R.id.arrow_backkkkk)

        val navController = findNavController(R.id.fragmentContainerView)
        bottomNav.setupWithNavController(navController)


        stateViewModel.cardVisible.observe(this){
            if (it != null){
                cwindowCard.isVisible = it
            }
        }

        stateViewModel.nameCards.observe(this){
            if (it != null){
                nameCards.text = it
            }
        }
        stateViewModel.bottomMenuVisible.observe(this){
            if (it != null){
                bottomNav.isVisible = it
            }
        }
        stateViewModel.arrowback.observe(this){
            if (it != null){
                arrowback.isVisible = it
            }
        }

        //For icons tint :)))))))
        bottomNav.itemIconTintList = null
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.latitude.toString())
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.longitude.toString())
    }

    override fun onObjectRemoved(p0: UserLocationView) {
        Log.e("test", "removed")
    }

    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.latitude.toString())
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.longitude.toString())
    }
}