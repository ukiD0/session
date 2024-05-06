/**
 * Author Korovkina Valentina
 * Created at 10/04/24
 *
 * */
package com.example.session2.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.databinding.FragmentTrackBinding
import com.example.session2.model.Orders
import com.example.session2.viewmodel.OrderViewModel
import com.example.session2.viewmodel.StateViewModel
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch


class TrackFragment : Fragment() {
    private lateinit var binding: FragmentTrackBinding
    private lateinit var stateViewModel: StateViewModel
    private lateinit var orderViewModel: OrderViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentTrackBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        stateViewModel.setVisible(false)
        stateViewModel.setBottomVisible(true)

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

        var trackkk: Orders? = null
        lifecycleScope.launch {
            trackkk = orderViewModel.getOrder()
        }.invokeOnCompletion {
            if (trackkk != null){
                binding.trackNumder.text = "R" + trackkk?.id.toString()
                lifecycleScope.launch {
                    orderViewModel.getOrderStatus(trackkk?.id!!)
                }

            }
            binding.progbar.isVisible = false
            binding.maincontainer.isVisible = true
        }
        orderViewModel.orderStatus.observe(viewLifecycleOwner){
            if (!it.isNullOrEmpty()){
                it.forEach { order ->
                    Log.e("test", order.toString())
                    when(order.status_id){
                        1 -> {
                            if (order.complated != null){
                                binding.checkStateOne.isChecked = order.complated!!
                            }else{
                                binding.checkStateOne.isEnabled = false
                            }
                        }
                        2 -> {
                            if (order.complated != null){
                                binding.checkStateTwo.isChecked = order.complated!!
                            }else{
                                binding.checkStateTwo.isEnabled = false
                            }
                        }
                        3 -> {
                            if (order.complated != null){
                                binding.checkStateThree.isChecked = order.complated!!
                            }else{
                                binding.checkStateThree.isEnabled = false
                            }
                        }
                        4 -> {
                            if (order.complated != null){
                                binding.checkStateFour.isChecked = order.complated!!
                            }else{
                                binding.checkStateFour.isEnabled = false
                            }
                        }
                    }
                }
            }
        }


        binding.viewpackageinfo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_trackFragment_to_trackingSecondFragment)
        }


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


}