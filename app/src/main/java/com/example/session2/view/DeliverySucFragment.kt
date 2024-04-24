package com.example.session2.view

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentDeliverySucBinding
import com.example.session2.model.Orders
import com.example.session2.viewmodel.OrderViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class DeliverySucFragment : Fragment() {
   private lateinit var binding: FragmentDeliverySucBinding
   private lateinit var orderViewModel: OrderViewModel
   var starsss: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliverySucBinding.inflate(inflater,container,false)
        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]

        var otziiv: Orders? = null
        binding.btndone.setOnClickListener {
            lifecycleScope.launch {
                try {
                    otziiv = orderViewModel.feedback(binding.textOTZUD.text.toString())
                }catch (e:Exception){
                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                }
            }.invokeOnCompletion {
                Navigation.findNavController(binding.root).navigate(R.id.action_deliverySucFragment_to_profileFragment)
            }
        }

        val orientationEventListener = object : OrientationEventListener(requireContext()){
            override fun onOrientationChanged(degrees: Int) {
                when (degrees){
                    50 -> {
                        Log.e("50","??")
                        addStars()
                    }
                    in 310..310 ->{
                        Log.e("310","??")
                        removeStars()
                    }
                }
            }

        }

        lifecycleScope.launch {
            orientationEventListener.disable()
            withContext(Dispatchers.IO){
                Thread.sleep(3500)
            }
        }.invokeOnCompletion {
            orientationEventListener.enable()
            binding.progbar.isVisible = false
            binding.imageCHECK.isVisible = true
            binding.deliverysuc.isVisible = true
            binding.textAfterDelivery.isVisible = true
        }


        return binding.root
    }
    private fun addStars(){
//        if(starsss in 0..4){
//            val star: ImageView = requireActivity().findViewById(R.id.stars)
//            star.setColorFilter(Color.YELLOW)
//            starsss++
//        }
//        if (starsss in 1..4){
//            val parent: LinearLayoutCompat = requireActivity().findViewById(R.id.stars)
//            val starArray = ArrayList<ImageView>()
//
//            for (i in 0 until parent.childCount){
//                val childView = parent.getChildAt(i)
//                if (childView is ImageView){
//                    starArray.add(childView)
//                }
//            }
//            if (starsss in 1 .. starArray.size){
//                val star: ImageView = starArray[starsss]
//                star.setColorFilter(Color.YELLOW)
//                starsss++
//            }
//
//        }
    }

    private fun removeStars() {
//        if(starsss in 0..5){
//            val star: ImageView = requireActivity().findViewById(R.id.stars)
//            star.setColorFilter(Color.GRAY)
//            starsss--
//        }
    }
//        if (starsss in 0..5) {
//            val parent: LinearLayoutCompat = requireActivity().findViewById(R.id.stars)
//            val starArray = ArrayList<ImageView>()
//
//            for (i in 0 until parent.childCount) {
//                val childView = parent.getChildAt(i)
//                if (childView is ImageView) {
//                    starArray.add(childView)
//                }
//            }
//            if (starsss in 1..starArray.size) {
//                val star: ImageView = starArray[starsss]
//                star.setColorFilter(Color.YELLOW)
//                starsss--
//            }
//
//        }
//    }

}