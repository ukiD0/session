/**
 * Author Korovkina Valentina
 * Created at 10/04/24
 *
 * */
package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import com.example.session2.R
import com.example.session2.databinding.FragmentWalletBinding
import com.example.session2.viewmodel.StateViewModel


class WalletFragment : Fragment() {
    private lateinit var binding: FragmentWalletBinding
    private lateinit var stateViewModel: StateViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWalletBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        stateViewModel.setVisible(false)


        return binding.root
    }

}