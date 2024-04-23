package com.example.session2.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.PointF
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.databinding.FragmentYandexMapBinding
import com.example.session2.viewmodel.PointsViewModel
import com.example.session2.viewmodel.StateViewModel
import com.squareup.picasso.Picasso
import com.squareup.picasso.PicassoProvider
import com.squareup.picasso.Target
import com.yandex.mapkit.MapKit
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.BoundingBox
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.layers.ObjectEvent
import com.yandex.mapkit.location.FilteringMode
import com.yandex.mapkit.location.Location
import com.yandex.mapkit.location.LocationListener
import com.yandex.mapkit.location.LocationStatus
import com.yandex.mapkit.map.Cluster
import com.yandex.mapkit.map.ClusterListener
import com.yandex.mapkit.map.ClusterTapListener
import com.yandex.mapkit.map.ClusterizedPlacemarkCollection
import com.yandex.mapkit.map.IconStyle
import com.yandex.mapkit.map.RotationType
import com.yandex.mapkit.render.internal.TextualImageProvider
import com.yandex.mapkit.user_location.UserLocationLayer
import com.yandex.mapkit.user_location.UserLocationObjectListener
import com.yandex.mapkit.user_location.UserLocationView
import com.yandex.runtime.image.ImageProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.launch
import java.lang.Exception


class YandexMapFragment : Fragment(), UserLocationObjectListener, ClusterTapListener, ClusterListener {
    private lateinit var binding: FragmentYandexMapBinding
    private lateinit var stateViewModel: StateViewModel
    private var userLocationLayer: UserLocationLayer? = null
    private var mapfack: MapKit? = null
    private lateinit var poinstViewModel: PointsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentYandexMapBinding.inflate(inflater,container,false)

        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        poinstViewModel = ViewModelProvider(requireActivity())[PointsViewModel::class.java]

        stateViewModel.setBottomVisible(false)
        stateViewModel.setVisible(true)
        stateViewModel.setTitle("Map")
        stateViewModel.setArrow(true)
        val arrback = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        arrback.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_yandexMapFragment2_to_profileFragment)
        }
        //val imageProvider = ImageProvider.fromResource(requireContext(),R.drawable.icon_png_pawww)
//        binding.mapview.map.mapObjects
//             .addPlacemark().apply {
//            geometry = Point(55.823493, 37.497698)
//            //setIcon(imageProvider)
//        }

        lifecycleScope.launch {
            poinstViewModel.getPoint()
        }
        poinstViewModel.points.observe(viewLifecycleOwner){ list ->
            if (!list.isNullOrEmpty()){
                val clusterizedCollection = binding.mapview.map.mapObjects.addClusterizedPlacemarkCollection(this@YandexMapFragment)
                val newPoinst:ArrayList<Point> = arrayListOf()
                val pinsCollection = binding.mapview.map.mapObjects.addCollection()
                Log.e("img",list.toString())
                list.forEach {point ->
//                    newPoinst.add(Point(point.latitude!!.toDouble(), point.longitude!!.toDouble() ))
//                    pinsCollection.addPlacemark().apply {
//                        geometry = Point(point.latitude!!.toDouble(),point.longitude!!.toDouble())
//                        setIcon(ImageProvider.fromBitmap(Picasso.get().load(point.pin_image).get()))
//                    }
//                    Log.e("test", point.toString())
                    Picasso.get().load(point.pin_image).into(object : Target{
                        override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                            Log.e("loadede","11")
                            clusterizedCollection.addPlacemark().apply {
                                geometry = Point(point.latitude!!.toDouble(),point.longitude!!.toDouble())
                                setIcon(ImageProvider.fromBitmap(bitmap))
                            }
                            clusterizedCollection.clusterPlacemarks(60.0, 15)
//
//                            pinsCollection.addPlacemark().apply {
//                            geometry = Point(point.latitude!!.toDouble(),point.longitude!!.toDouble())
//                            setIcon(ImageProvider.fromBitmap(bitmap))
//                             }
                        }

                        override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                            Log.e("loadedFailed","123")
                        }

                        override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                            Log.e("preparedLoaded","323")
                        }

                    })

                }
//                clusterizedCollection.addEmptyPlacemarks(
//                    newPoinst)

            }
        }

        mapfack = MapKitFactory.getInstance()
        mapfack!!.resetLocationManagerToDefault()
        userLocationLayer = mapfack!!.createUserLocationLayer(binding.mapview.mapWindow)
        userLocationLayer!!.isVisible = true
        userLocationLayer!!.isHeadingEnabled = true
        userLocationLayer!!.setObjectListener(this)
        mapfack!!.createLocationManager().requestSingleUpdate(object : LocationListener{
            override fun onLocationUpdated(p0: Location) {
                Log.e("long", p0.position.longitude.toString())
                Log.e("lati", p0.position.latitude.toString())
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {
            }

        })
        mapfack!!.createLocationManager().subscribeForLocationUpdates(0.0, 0, 0.0, true, FilteringMode.ON, object : LocationListener{
            override fun onLocationUpdated(p0: Location) {
                Log.e("long", p0.position.longitude.toString())
                Log.e("lati", p0.position.latitude.toString())
            }

            override fun onLocationStatusUpdated(p0: LocationStatus) {
                TODO("Not yet implemented")
            }
        })


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
//        mapfack.createLocationManager().requestSingleUpdate(object : LocationListener{
//            override fun onLocationUpdated(location: Location) {
//                Log.e("TagCheck1", "LocationUpdated " + location.position.longitude)
//                Log.e("TagCheck1", "LocationUpdated " + location.position.latitude)
//                binding.mapview.map.move(
//                    CameraPosition(location.position,14.0f, 0.0f, 0.0f),
//                    Animation(Animation.Type.SMOOTH, 1f),
//                    null
//                )
//            }
//
//            override fun onLocationStatusUpdated(location: LocationStatus) {
//                Log.e("TagCheck2", "LocationUpdated " + location.name.toString())
//            }
//
//        })

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

//        mapfack.createLocationManager().subscribeForLocationUpdates(0.0, 0, 0.0, true,
//            FilteringMode.ON,
//            object : LocationListener {
//                override fun onLocationUpdated(location: Location) {
//                    Log.d("TagCheck3", "LocationUpdated " + location.position.longitude)
//                    Log.d("TagCheck3", "LocationUpdated " + location.position.latitude)
//                }
//
//                override fun onLocationStatusUpdated(locationStatus: LocationStatus) {}
//            })
//
//        mapfack.onStart()
//        binding.mapview.onStart()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapview.onStart()
    }

    override fun onStop() {
        binding.mapview.onStart()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onObjectAdded(userLocationView: UserLocationView) {
//        userLocationLayer!!.setAnchor(
//            PointF((binding.mapview.getWidth() * 0.5).toFloat(), (binding.mapview.getHeight() * 0.5) as Float),
//            PointF((binding.mapview.getWidth() * 0.5).toFloat(), (binding.mapview.getHeight() * 0.83) as Float)
//        )

        userLocationView.arrow.setIcon(
            ImageProvider.fromResource(
                requireContext(), R.drawable.icon_png_pawww
            ),
            IconStyle().setScale(0.10f)
        )
//
//        val pinIcon = userLocationView.pin.useCompositeIcon()
//
//        pinIcon.setIcon(
//            "icon",
//            ImageProvider.fromResource(requireContext(), R.drawable.icon_png_pawww),
//            IconStyle().setAnchor(PointF(0f, 0f))
//                .setRotationType(RotationType.ROTATE)
//                .setZIndex(0f)
//                .setScale(1f)
//        )
//
//        pinIcon.setIcon(
//            "pin",
//            ImageProvider.fromResource(requireContext(), R.drawable.icon_png_pawww),
//            IconStyle().setAnchor(PointF(0.5f, 0.5f))
//                .setRotationType(RotationType.ROTATE)
//                .setZIndex(1f)
//                .setScale(0.5f)
//        )
//
        userLocationView.accuracyCircle.fillColor = Color.TRANSPARENT
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.latitude.toString())
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.longitude.toString())
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.pin.geometry.latitude.toString())
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.pin.geometry.longitude.toString())
    }

    override fun onObjectRemoved(p0: UserLocationView) {
        Log.e("test", "removed")
    }

    override fun onObjectUpdated(userLocationView: UserLocationView, p1: ObjectEvent) {
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.latitude.toString())
        Log.e(YandexMapFragment::class.java.simpleName, userLocationView.arrow.geometry.longitude.toString())
    }

    override fun onClusterTap(p0: Cluster): Boolean {
        Toast.makeText(
            this@YandexMapFragment.requireContext(),
            p0.size.toString(),
            Toast.LENGTH_LONG
        ).show()
        return true
    }

    override fun onClusterAdded(cluster: Cluster) {
        cluster.appearance.useCompositeIcon().apply {
            setIcon(
                "text",
                TextualImageProvider(cluster.size.toString(), 20, true, Color.RED, Color.WHITE),
                IconStyle().setScale(2f)
            )
            setIcon(
                "background",
                ImageProvider.fromResource(requireContext(), R.drawable.icon_png_pawww),
                IconStyle().setScale(0.3f)
            )

        }
        cluster.addClusterTapListener(this@YandexMapFragment)
    }

}