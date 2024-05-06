package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentTrackingSecondBinding
import com.example.session2.model.Orders
import com.example.session2.viewmodel.OrderViewModel
import com.example.session2.viewmodel.StateViewModel
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch

class TrackingSecondFragment : Fragment() {
   private lateinit var binding: FragmentTrackingSecondBinding
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
        binding = FragmentTrackingSecondBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        stateViewModel.setVisible(false)
        stateViewModel.setBottomVisible(true)




        var track: Orders? = null
        lifecycleScope.launch {
            try {
                track = orderViewModel.getOrder()
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }.invokeOnCompletion {
            if (track != null){
                binding.trackNumder.text = "R" + track?.id.toString()
            }
            binding.progbar.isVisible = false
            binding.maincontainer.isVisible = true
        }

        binding.viewpackageinfo.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_trackingSecondFragment_to_sendAPAckageFragment2)
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