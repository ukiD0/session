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
import com.example.session2.model.destinations_details
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
            Navigation.findNavController(binding.root).navigate(R.id.action_sendAPAckageFragment_to_trackingSecondFragment2)
        }

        var orig_adress: destinations_details? = null
        var or_phone: destinations_details? = null
//        lifecycleScope.launch {
//            try {
//                orig_adress = destinationViewModel.getDestinDetail()
//            }catch (e:Exception){
//                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
//            }
//        }.invokeOnCompletion {
//            if (orig_adress !=null){
//               binding.origAdresssss.text = orig_adress.toString()
//            }
//        }
        var cur_city: Orders? = null
        var cur_address: Orders? = null
        var cur_phone: Orders? = null
        var pack_it: Orders? = null
        var weight: Orders? = null
        var worth: Orders? = null
        var track_number: Orders? = null
        var charges: Orders? = null
        var instant: Orders? = null
        var tax: Orders? = null
        var total: Orders?  =null

        lifecycleScope.launch {
            try {
                cur_address = orderViewModel.getOrder()
                cur_city = orderViewModel.getOrder()
                pack_it = orderViewModel.getOrder()
                weight = orderViewModel.getOrder()
                worth = orderViewModel.getOrder()
                track_number = orderViewModel.getOrder()
                cur_phone = orderViewModel.getOrder()
                charges = orderViewModel.getOrder()
                instant = orderViewModel.getOrder()
                tax = orderViewModel.getOrder()
                total = orderViewModel.getOrder()
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }.invokeOnCompletion {
            if (cur_address != null
                && cur_city != null
                &&pack_it != null
                && weight != null
                && worth != null
                && track_number != null
                && cur_phone != null
                && charges != null
                && instant != null
                && tax != null
                && total != null){
                binding.city.text = cur_city?.country.toString() + " " + cur_address?.address.toString()
                binding.packitemss.text = pack_it?.package_items.toString()
                binding.weighkg.text = weight?.weight_items.toString() + "kg"
                binding.Nworth.text = "N" + worth?.worth_items.toString()
                binding.Rtrack.text = "R-" + track_number?.id.toString()
                binding.telphone.text = cur_phone?.phone.toString()
                binding.Ncharrges.text = "N" + charges?.delivery_charges.toString()
                binding.Ntax.text = "N" + tax?.tax_and_service_charges.toString()
                binding.Ninstant.text = "N" + instant?.instant_delivery.toString()
                binding.total.text = "N" + total?.sum_price.toString()
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