/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.session2.viewmodel.StateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var stateViewModel: StateViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
}