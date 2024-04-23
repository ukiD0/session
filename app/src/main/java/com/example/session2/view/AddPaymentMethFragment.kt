package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.databinding.FragmentAddPaymentMethBinding
import com.example.session2.viewmodel.StateViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView

class AddPaymentMethFragment : Fragment() {
    private lateinit var binding: FragmentAddPaymentMethBinding
    private lateinit var stateViewModel: StateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPaymentMethBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        stateViewModel.setVisible(true)
        stateViewModel.setArrow(true)
        stateViewModel.setBottomVisible(false)
        stateViewModel.setTitle("Add Payment method")
        val btnback = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        btnback.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_addPaymentMethFragment_to_walletTransactionFragment)
        }

        //кнопочки
        binding.rb2.setOnClickListener{
            binding.cards.visibility = View.VISIBLE
            binding.radioButton.isChecked = false
        }
        binding.radioButton.setOnClickListener {
            binding.rb2.isChecked = false
            binding.cards.visibility = View.GONE
        }
        binding.rb3.setOnClickListener{
            binding.rb4.isChecked = false
        }
        binding.rb4.setOnClickListener{
            binding.rb3.isChecked = false
        }

        return binding.root
    }

}