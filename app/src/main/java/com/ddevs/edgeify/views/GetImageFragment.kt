package com.ddevs.edgeify.views

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.URLUtil
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ddevs.edgeify.MainViewModel
import com.ddevs.edgeify.R
import com.ddevs.edgeify.databinding.FragmentGetImageBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File


class GetImageFragment : Fragment() {
    lateinit var binding:FragmentGetImageBinding
    val viewModel:MainViewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    val imagePic = registerForActivityResult(ActivityResultContracts.PickVisualMedia()){
        if (it != null) {
            val bitmap = MediaStore.Images.Media.getBitmap(requireActivity().contentResolver,it)
            viewModel.setBitmap(bitmap)
            viewModel.setUri(it)
        }
        Log.d("Uri",it.toString())
        findNavController().navigate(R.id.action_getImageFragment_to_previewImageFragment)
    }
    val captureImage = registerForActivityResult(ActivityResultContracts.TakePicture()){
        if(it) {
            viewModel.setUri(mainUri)
            val bitmap =
                MediaStore.Images.Media.getBitmap(requireActivity().contentResolver, mainUri)
            viewModel.setBitmap(bitmap)
            Log.d("Uri", mainUri.toString())
            findNavController().navigate(R.id.action_getImageFragment_to_previewImageFragment)
        }
    }
    lateinit var mainUri:Uri
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_get_image, container, false)
        binding.pickBtn.setOnClickListener {
            imagePic.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }
        binding.urlApplyBtn.setOnClickListener {
            if(URLUtil.isValidUrl(binding.imageUrl.text.toString())){
                viewModel.loading.value =true
                Glide.with(requireContext()).asBitmap()
                    .load(binding.imageUrl.text.toString()).into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            viewModel.loading.value = false
                            viewModel.setBitmap(resource)
                            findNavController().navigate(R.id.action_getImageFragment_to_previewImageFragment)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {


                        }
                    })
            }
            else{
                viewModel.loading.value = false
                Snackbar.make(binding.root,"Please enter the correct url",Snackbar.LENGTH_SHORT).show()
            }
        }
        binding.captureBtn.setOnClickListener {
            val getImage: File? = requireActivity().externalCacheDir
            if (getImage != null) {
                mainUri = FileProvider.getUriForFile(requireContext(),requireActivity().applicationContext.packageName + ".provider",File(getImage.path, "profile.png"))
            }
            captureImage.launch(mainUri)
        }
        return binding.root
    }
}