package com.example.session2.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentDeliverySucBinding
import com.example.session2.domain.StarInerface
import com.example.session2.model.Orders
import com.example.session2.viewmodel.OrderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DeliverySucFragment : Fragment(), StarInerface {
    private lateinit var binding: FragmentDeliverySucBinding
    private lateinit var orderViewModel: OrderViewModel

    override var starsss: Int = 0
    private lateinit var orientationEventListener: OrientationEventListener
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliverySucBinding.inflate(inflater,container,false)
        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        lifecycleScope.launch {
            withContext(Dispatchers.IO){
                Thread.sleep(3500)
            }
        }.invokeOnCompletion {
            orientationEventListener = object : OrientationEventListener(requireContext()){
                override fun onOrientationChanged(degrees: Int) {
                    when (degrees){
                        50 -> {
                            Log.e("50","??")
                            addStars(binding.stars)
                        }
                        in 310..310 ->{
                            Log.e("310","??")
                            removeStars(binding.stars)
                        }
                    }
                }
            }
            orientationEventListener.enable()
            binding.progbar.isVisible = false
            binding.imageCHECK.isVisible = true
            binding.deliverysuc.isVisible = true
            binding.textAfterDelivery.isVisible = true
        }
        var otziiv: Orders? = null
        binding.btndone.setOnClickListener {
            lifecycleScope.launch {
                try {
                    otziiv = orderViewModel.feedback(binding.textOTZUD.text.toString(), starsss)
                }catch (e:Exception){
                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                }
            }.invokeOnCompletion {
                if (otziiv != null){
                    Navigation.findNavController(binding.root).navigate(R.id.action_deliverySucFragment_to_homeFragment)
                }
            }
        }


        return binding.root
    }




}
