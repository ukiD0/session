package com.example.session2.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentMapBinding
import com.example.session2.viewmodel.StateViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.library.BuildConfig
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import kotlin.coroutines.coroutineContext


class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private lateinit var stateViewModel: StateViewModel
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMapBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]

        stateViewModel.setVisible(true)
        stateViewModel.setBottomVisible(false)
        stateViewModel.setArrow(true)
        stateViewModel.setTitle("Map")
        val arrback = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        arrback.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_mapFragment_to_profileFragment)
        }

        //start work with map
        binding.map.setTileSource(TileSourceFactory.MAPNIK)
        binding.map.controller.setZoom(5.0)
        Configuration.getInstance().userAgentValue = BuildConfig.LIBRARY_PACKAGE_NAME
        //zoom button
        binding.map.setBuiltInZoomControls(true)
        //zomm by 2 fingers
        binding.map.setMultiTouchControls(true)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),123)
        }else{
            Log.e("else","im here")
            //osm
            val  mLocationOverlay = MyLocationNewOverlay(GpsMyLocationProvider(requireActivity()), binding.map)
            mLocationOverlay.enableMyLocation()
//            binding.map.overlays.add(mLocationOverlay)
            val myMarker= Marker(binding.map)
            val marker2 = Marker(binding.map)
            val marker3 = Marker(binding.map)
            myMarker.icon = ContextCompat.getDrawable(requireActivity(),R.drawable.icon_paw)
            lifecycleScope.launch {
                var firstTime = true
                while (kotlin.coroutines.coroutineContext.isActive){
                    try {
                        myMarker.position = GeoPoint(mLocationOverlay.myLocation.latitude,mLocationOverlay.myLocation.longitude)
                        binding.map.overlays.add(myMarker)
                        if (firstTime){
                            binding.map.controller.setCenter(myMarker.position)
                            binding.map.controller.setZoom(14.0)
                            firstTime = false
                        }
                    }catch (e:Exception){
                    }
                    delay(5000)
                }
            }
            marker2.position = GeoPoint(55.844708, 37.575500)
            binding.map.overlays.add(marker2)

            //google lib for cords and set maerker by osm
//            fusedLocationClient.lastLocation
//                .addOnSuccessListener { location : Location? ->
//                    Log.e("else2","im here 2")
//                    val myMarker = Marker(binding.map)
//                    myMarker.icon = ContextCompat.getDrawable(requireContext(),R.drawable.icon_paw)
//                    myMarker.position = GeoPoint(location!!.latitude,location.longitude)
//                    binding.map.overlays.add(myMarker)
//
//                }.addOnFailureListener{
//                    Log.e("else2","im here 2")
//                    val myMarker = Marker(binding.map)
//                    myMarker.icon = ContextCompat.getDrawable(requireContext(),R.drawable.icon_paw)
//                    myMarker.position = GeoPoint(0.0,0.0)
//                    binding.map.overlays.add(myMarker)
//                    com.example.session2.common.Helper.alert(requireContext(),"Ошибка","Локация не работает на эмуляторе, подключите устройство")
//                }
        }

        return binding.root
    }
}