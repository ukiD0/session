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
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentOTPVerBinding


class OTPVerFragment : Fragment() {

    private lateinit var binding: FragmentOTPVerBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOTPVerBinding.inflate(inflater,container,false)

        binding.oOne.doAfterTextChanged {
            if (binding.oOne.text.toString().length == 1){
                binding.oOne.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_blue))
            }else{
                binding.oOne.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_gray))
            }
        }
        binding.oTwo.doAfterTextChanged {
            if (binding.oTwo.text.toString().length == 1){
                binding.oTwo.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_blue))
            }else{
                binding.oTwo.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_gray))
            }
        }
        binding.oThree.doAfterTextChanged {
            if (binding.oThree.text.toString().length == 1){
                binding.oThree.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_blue))
            }else{
                binding.oThree.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_gray))
            }
        }
        binding.oFour.doAfterTextChanged {
            if (binding.oFour.text.toString().length == 1){
                binding.oFour.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_blue))
            }else{
                binding.oFour.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_gray))
            }
        }
        binding.oFive.doAfterTextChanged {
            if (binding.oFive.text.toString().length == 1){
                binding.oFive.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_blue))
            }else{
                binding.oFive.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_gray))
            }
        }
        binding.oSix.doAfterTextChanged {
            if (binding.oSix.text.toString().length == 1){
                binding.oSix.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_blue))
            }else{
                binding.oSix.setBackgroundDrawable(resources.getDrawable(R.drawable.otp_gray))
            }
        }

        binding.oSix.doAfterTextChanged {
            try {
                if (binding.oOne.text.toString().length == 1
                    && binding.oTwo.text.toString().length == 1
                    && binding.oThree.text.toString().length == 1
                    && binding.oFour.text.toString().length == 1
                    && binding.oFive.text.toString().length == 1
                    && binding.oSix.text.toString().length == 1){
                    binding.sendOTP.isEnabled = true
                }else{
                    binding.sendOTP.isEnabled = false
                }
            }catch (e:Exception){
                Helper.alert(requireContext(),resources.getString(R.string.error),resources.getString(R.string.error_email_mes))
            }
        }
        return binding.root
    }

}