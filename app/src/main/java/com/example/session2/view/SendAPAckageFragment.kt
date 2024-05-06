package com.example.session2.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentSendAPAckageBinding
import com.example.session2.model.Orders
import com.example.session2.viewmodel.destinations_details
import com.example.session2.viewmodel.DestinationViewModel
import com.example.session2.viewmodel.OrderViewModel
import com.example.session2.viewmodel.StateViewModel
import kotlinx.coroutines.launch

class SendAPAckageFragment : Fragment() {
    private lateinit var binding: FragmentSendAPAckageBinding
    private lateinit var stateViewModel: StateViewModel
    private lateinit var orderViewModel: OrderViewModel
    private lateinit var destinationViewModel: DestinationViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSendAPAckageBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        orderViewModel = ViewModelProvider(requireActivity())[OrderViewModel::class.java]
        destinationViewModel = ViewModelProvider(requireActivity())[DestinationViewModel::class.java]

        stateViewModel.setTitle("Send a package")
        stateViewModel.setBottomVisible(false)
        stateViewModel.setArrow(true)
        stateViewModel.setVisible(true)

        val backpls = requireActivity().findViewById<ImageView>(R.id.arrow_backkkkk)
        backpls.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_sendAPAckageFragment_to_deliverySucFragment)
        }

        var orig_adress: destinations_details? = null
        var order: Orders? = null

        lifecycleScope.launch {
            try {
                order = orderViewModel.getOrder()
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }.invokeOnCompletion {
            if (order != null){
                lifecycleScope.launch {
                    try {
                        orig_adress = destinationViewModel.getDestinDetail(order!!.id)
                    }catch (e:Exception){
                        Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                    }
                }.invokeOnCompletion {
                    if (orig_adress !=null){
                        binding.origAdresssss.text = if (orig_adress?.country != null && orig_adress?.address != null) orig_adress?.country.toString() + " " + orig_adress?.address.toString() else  binding.origAdresssss.text
                        binding.origPhone.text = if (orig_adress?.phone != null) orig_adress?.phone.toString() else  binding.origPhone.text
                    }
                }
                binding.city.text = if (order?.country != null) order?.country.toString() + " " + order?.address.toString() else  binding.city.text
                binding.packitemss.text = if (order?.package_items != null) order?.package_items else binding.packitemss.text
                binding.weighkg.text = if (order?.weight_items != null) order?.weight_items.toString() + "kg" else binding.weighkg.text
                binding.Nworth.text = if (order?.worth_items != null) "N" + order?.worth_items else binding.Nworth.text
                binding.Rtrack.text = if (order?.id != null) "R-" + order?.id else binding.Rtrack.text
                binding.telphone.text = if (order?.phone != null) order?.phone else binding.telphone.text
                binding.Ncharrges.text = if (order?.delivery_charges != null) "N" + order?.delivery_charges else binding.Ncharrges.text
                binding.Ntax.text = if (order?.tax_and_service_charges != null)  "N" + order?.tax_and_service_charges else binding.Ntax.text
                binding.Ninstant.text = if (order?.instant_delivery != null) "N" + order?.instant_delivery else binding.Ninstant.text
                binding.total.text = if (order?.sum_price != null) "N" + order?.sum_price else binding.total.text
            }
            binding.progBar.isVisible = false
            binding.maincontainer.isVisible = true
        }

        binding.suc.setOnClickListener{
            Navigation.findNavController(binding.root).navigate(R.id.action_sendAPAckageFragment_to_deliverySucFragment)
        }

        return binding.root
    }

}