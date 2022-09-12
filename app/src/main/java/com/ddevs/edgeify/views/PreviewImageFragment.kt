package com.ddevs.edgeify.views

import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ddevs.edgeify.MainViewModel
import com.ddevs.edgeify.R
import com.ddevs.edgeify.databinding.FragmentPreviewImageBinding
import java.io.*


class PreviewImageFragment : Fragment() {
    val viewModel:MainViewModel by lazy{
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    lateinit var binding:FragmentPreviewImageBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_preview_image, container, false)
        Log.d("Uri1",viewModel.getUri().toString())
        var file:File = File(viewModel.getUri().path)
        Glide.with(binding.root)
            .load(viewModel.getBitmap()).into(binding.imageView)
        binding.cancelButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.confirmButton.setOnClickListener {
            var file:File = File(requireContext().cacheDir,"final.jpeg")
            file.createNewFile()
            var bitmap = viewModel.getBitmap()
            var bos = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG,0,bos);
            var bitmapdata = bos.toByteArray()
            var fos:FileOutputStream? = null;
            try {
                fos = FileOutputStream(file)
            } catch (e: FileNotFoundException) {
                e.printStackTrace()
            }
            try {
                fos?.write(bitmapdata)
                fos?.flush()
                fos?.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
            viewModel.processImage(file)
            findNavController().navigate(R.id.action_previewImageFragment_to_resultFragment)
        }
        return binding.root
    }
}