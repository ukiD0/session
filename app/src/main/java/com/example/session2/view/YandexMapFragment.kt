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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.APIkey
import com.example.session2.databinding.FragmentYandexMapBinding
import com.example.session2.viewmodel.StateViewModel
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.CameraPosition
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import org.osmdroid.util.GeoPoint


class YandexMapFragment : Fragment() {
    private lateinit var binding: FragmentYandexMapBinding
    private lateinit var stateViewModel: StateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.setApiKey(APIkey.MAPKIT_API_KEY)
        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYandexMapBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]

        stateViewModel.setBottomVisible(false)
        stateViewModel.setVisible(true)
        stateViewModel.setTitle("Map")
        stateViewModel.setArrow(true)
        val arrback = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        arrback.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_yandexMapFragment2_to_profileFragment)
        }
        val imageProvider = ImageProvider.fromResource(requireContext(),R.drawable.icon_png_pawww)
        binding.mapview.map.mapObjects
             .addPlacemark().apply {
            geometry = Point(55.823493, 37.497698)
            geometry = Point(55.815212, 37.490007)
            geometry = Point(55.825152, 37.494732)
            setIcon(imageProvider)
        }



        val mapfack = MapKitFactory.getInstance()
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION),123)
        }
        mapfack.createLocationManager().requestSingleUpdate(object : LocationListener{
            override fun onLocationUpdated(location: Location) {
                Log.e("TagCheck1", "LocationUpdated " + location.position.longitude)
                Log.e("TagCheck1", "LocationUpdated " + location.position.latitude)
                binding.mapview.map.move(
                    CameraPosition(location.position,14.0f, 0.0f, 0.0f),
                    Animation(Animation.Type.SMOOTH, 1f),
                    null
                )
            }

            override fun onLocationStatusUpdated(location: LocationStatus) {
                Log.e("TagCheck2", "LocationUpdated " + location.name.toString())
            }

        })

//        lifecycleScope.launch {
//            var firstTime = true
//            var
//            while (kotlin.coroutines.coroutineContext.isActive){
//                try {
//                    binding.mapview.map.move(CameraPosition(Point())) .position = GeoPoint(mLocationOverlay.myLocation.latitude,mLocationOverlay.myLocation.longitude)
//                    binding.map.overlays.add(myMarker)
//                    if (firstTime){
//                        binding.map.controller.setCenter(myMarker.position)
//                        binding.map.controller.setZoom(14.0)
////                        firstTime = false
//                    }
//                }catch (e:Exception){
//                }
//                delay(5000)
//            }
//        }

        mapfack.createLocationManager().subscribeForLocationUpdates(0.0, 0, 0.0, true,
            FilteringMode.ON,
            object : LocationListener {
                override fun onLocationUpdated(location: Location) {
                    Log.d("TagCheck3", "LocationUpdated " + location.position.longitude)
                    Log.d("TagCheck3", "LocationUpdated " + location.position.latitude)
                }

                override fun onLocationStatusUpdated(locationStatus: LocationStatus) {}
            })

        mapfack.onStart()
        binding.mapview.onStart()


        return binding.root
    }


}