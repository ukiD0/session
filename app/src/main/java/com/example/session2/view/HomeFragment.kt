/**
 * Author Korovkina Valentina
 * Created at 09/04/24
 *
 * */
package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.session2.databinding.FragmentHomeBinding
import com.example.session2.viewmodel.StateViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var stateViewModel: StateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]


        stateViewModel.setBottomVisible(true)
        stateViewModel.setVisible(false)
//        val menu = requireActivity().findViewById<BottomNavigationView>(R.id.bottomnav)
//        menu.isVisible = true
//        val cards = requireActivity().findViewById<CardView>(R.id.cwindowCard)
//        cards.isVisible = false

        return binding.root
    }

}