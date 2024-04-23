package com.example.session2.view

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.databinding.FragmentDeliverySucBinding

class DeliverySucFragment : Fragment() {
   private lateinit var binding: FragmentDeliverySucBinding
   private var stars = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDeliverySucBinding.inflate(inflater,container,false)

        binding.btndone.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_deliverySucFragment_to_profileFragment)
        }

//        val orientationEventListener = object : OrientationEventListener(requireContext()){
//            override fun onOrientationChanged(degrees: Int) {
//                when (degrees){
//                    50 -> {
//                        addStars()
//                    }
//                    in 310..310 ->{
//                        removeStars()
//                    }
//                }
//            }
//
//        }

        return binding.root
    }
//    private fun addStars(){
//        if (stars in 1..4){
//            val star: ImageView = requireActivity().findViewById(R.id.stars)
//            star.setColorFilter(Color.YELLOW)
//            stars++
//        }
//    }
//
//    private fun removeStars(){
//        if (stars  in 0..5){
//            val star: ImageView = requireActivity().findViewById(R.id.stars)
//            star.setColorFilter(Color.GRAY)
//            stars--
//        }
//    }

}