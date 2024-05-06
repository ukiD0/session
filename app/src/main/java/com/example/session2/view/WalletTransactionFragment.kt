package com.example.session2.view

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.session2.R
import com.example.session2.common.Helper
import com.example.session2.databinding.FragmentTransactListBinding
import com.example.session2.model.Profiles
import com.example.session2.viewmodel.BucketViewModel
import com.example.session2.viewmodel.ProfileViewModel
import com.example.session2.viewmodel.StateViewModel
import com.example.session2.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.InputStream


class WalletTransactionFragment : Fragment() {

    private lateinit var binding: FragmentTransactListBinding
    private lateinit var stateViewModel: StateViewModel
    private lateinit var transactionViewModel: TransactionViewModel
    private lateinit var profileViewModel: ProfileViewModel
    private lateinit var bucketViewModel: BucketViewModel
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()){
        binding.photo.setImageURI(it)
        sendImageToServer(it!!)
    }

    val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
        if (it.resultCode == Activity.RESULT_OK){
            //Log.e(WalletTransactionFragment::class.java.simpleName, it.data?.extras?.get("data") as Bitmap)

            val res = it.data!!.extras?.get("data") as Bitmap
            binding.photo.setImageBitmap(res)
            sendImageToServer(res)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTransactListBinding.inflate(inflater,container,false)
        stateViewModel = ViewModelProvider(requireActivity())[StateViewModel::class.java]
        transactionViewModel = ViewModelProvider(requireActivity())[TransactionViewModel::class.java]
        profileViewModel = ViewModelProvider(requireActivity())[ProfileViewModel::class.java]
        bucketViewModel = ViewModelProvider(requireActivity())[BucketViewModel::class.java]

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

        var imageDonwload: ByteArray? = null
        lifecycleScope.launch {
            try {
                imageDonwload = bucketViewModel.getImage()
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }.invokeOnCompletion {
            if (imageDonwload != null){
                binding.photo.setImageBitmap(BitmapFactory.decodeByteArray(imageDonwload,0,imageDonwload!!.size))
            }
            binding.mainContainer.isVisible = true
            binding.progBar.isVisible = false
        }

        try {
            lifecycleScope.launch {
                transactionViewModel.getTransaction()
            }
        }catch (e:Exception){
            Helper.alert(requireActivity(),e.cause.toString(),e.message.toString())
        }

        var name: Profiles? = null
        try {
            lifecycleScope.launch {
               name =  profileViewModel.getProfileData()
            }.invokeOnCompletion {
                if (name != null){
                    binding.textHello.setText("Hello " + name?.fullname.toString().split(" ")[0])
                }
            }
        }catch (e:Exception){
            Helper.alert(requireActivity(),e.cause.toString(),e.message.toString())
        }
        var money: Profiles? = null
        try {
            lifecycleScope.launch {
                money = profileViewModel.getProfileData()
            }.invokeOnCompletion {
                if (money != null){
                    binding.moneyText.setText("N" + money?.balance.toString())
                }
            }
        }catch (e:Exception){
            Helper.alert(requireActivity(),e.cause.toString(),e.message.toString())
        }

        var image: ByteArray? = null
        lifecycleScope.launch {
            try {
                image = bucketViewModel.getImage()
            }catch (e:Exception){
                Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
            }
        }.invokeOnCompletion {
            if (image != null){
                binding.photo.setImageBitmap(BitmapFactory.decodeByteArray(image,0,image!!.size))
            }
            binding.mainContainer.isVisible = true
            binding.progBar.isVisible = false
        }

        binding.paymentmethod.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.action_walletTransactionFragment_to_addPaymentMethFragment)
        }



        binding.photo.setOnClickListener {
            showImagePickerDialog(requireContext())
//            ImagePicker.with(this)
//                .compress(1024)			//Final image size will be less than 1 MB(Optional)
//                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
//                .start()
        }



        return binding.root
    }
    fun showImagePickerDialog(context: Context){
        val option = arrayOf("Gallery", "Camera")
        AlertDialog.Builder(context)
            .setTitle("Выберите источник изображения")
            .setItems(option){_,which ->
                when    (which){
                    0 -> openGallery()
                    1 -> openCamera()
                }
            }
            .show()
    }
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    private fun openCamera() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.CAMERA
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(requireActivity(), arrayOf(
                Manifest.permission.CAMERA),123)
        }else{
            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            val values:ContentValues = ContentValues()
//            intent.putExtra(MediaStore.EXTRA_OUTPUT, values)
            cameraLauncher.launch(intent)
        }

    }
    private fun sendImageToServer(uri: Uri){
        var inputStream : InputStream? = null
        binding.progBar.isVisible = true
        binding.mainContainer.isVisible = false
        try {
            inputStream = requireActivity().contentResolver.openInputStream(uri)
            val bitMap = BitmapFactory.decodeStream(inputStream)
            val outPutStream = ByteArrayOutputStream()
            bitMap.compress(Bitmap.CompressFormat.JPEG,100,outPutStream)
            lifecycleScope.launch{
                try {
                    bucketViewModel.uploadImage(outPutStream.toByteArray())
                }catch (e:Exception){
                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                }
            }.invokeOnCompletion {
                binding.progBar.isVisible = false
                binding.mainContainer.isVisible = true
            }
        }catch (e:Exception){
            Log.e("error",e.cause.toString())
        }finally {
            inputStream?.close()
        }
    }
    private fun sendImageToServer(bitmap: Bitmap){
        binding.progBar.isVisible = true
        binding.mainContainer.isVisible = false
        try {
            val outPutStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,outPutStream)
            lifecycleScope.launch{
                try {
                    bucketViewModel.uploadImage(outPutStream.toByteArray())
                }catch (e:Exception){
                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
                }
            }.invokeOnCompletion {
                binding.progBar.isVisible = false
                binding.mainContainer.isVisible = true
            }
        }catch (e:Exception){
            Log.e("error",e.cause.toString())
        }
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        binding.photo.setImageURI(data?.data!!)
//        var inputStream : InputStream? = null
//        binding.progBar.isVisible = true
//        binding.mainContainer.isVisible = false
//        try {
//            inputStream = requireActivity().contentResolver.openInputStream(data?.data!!)
//            val bitMap = BitmapFactory.decodeStream(inputStream)
//            val outPutStream = ByteArrayOutputStream()
//            bitMap.compress(Bitmap.CompressFormat.JPEG,100,outPutStream)
//            lifecycleScope.launch{
//                try {
//                    bucketViewModel.uploadImage(outPutStream.toByteArray())
//                }catch (e:Exception){
//                    Helper.alert(requireContext(),e.cause.toString(),e.message.toString())
//                }
//            }.invokeOnCompletion {
//                binding.progBar.isVisible = false
//                binding.mainContainer.isVisible = true
//            }
//        }catch (e:Exception){
//            Log.e("error",e.cause.toString())
//        }finally {
//            inputStream?.close()
//        }
//    }




}