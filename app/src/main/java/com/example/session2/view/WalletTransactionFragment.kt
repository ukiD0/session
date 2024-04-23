package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.session2.R
import com.example.session2.databinding.FragmentTransactListBinding
import com.example.session2.model.Transactions
import com.example.session2.viewmodel.StateViewModel
import com.example.session2.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch


class WalletTransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactListBinding
    private lateinit var stateViewModel: StateViewModel
    private lateinit var transactionViewModel: TransactionViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactListBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        transactionViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]

        stateViewModel.setVisible(true)
        stateViewModel.setTitle("Wallet")
        stateViewModel.setBottomVisible(true)
        stateViewModel.setArrow(false)

        val linerlayoutmanager = LinearLayoutManager(requireActivity())


        transactionViewModel.trans.observe(viewLifecycleOwner){
            if (it != null){
                val adapter = MytransactionRecyclerViewAdapter(it,requireActivity())
                binding.list.layoutManager = linerlayoutmanager
                binding.list.adapter = adapter
            }
        }
//        lifecycleScope.launch {
//            transactionViewModel.getTransaction(linerlayoutmanager)
//        }


        binding.paymentmethod.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_walletTransactionFragment_to_addPaymentMethFragment)
        }



        return binding.root
    }


}