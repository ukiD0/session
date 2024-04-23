package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.databinding.FragmentSendAPAckageBinding
import com.example.session2.viewmodel.StateViewModel

class SendAPAckageFragment : Fragment() {
    private lateinit var binding: FragmentSendAPAckageBinding
    private lateinit var stateViewModel: StateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendAPAckageBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]

        stateViewModel.setTitle("Send a package")
        stateViewModel.setBottomVisible(false)
        stateViewModel.setArrow(true)
        stateViewModel.setVisible(true)

        val backpls = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        backpls.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_sendAPAckageFragment_to_trackFragment)
        }
        binding.suc.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_sendAPAckageFragment_to_deliverySucFragment)
        }

        return binding.root
    }

}