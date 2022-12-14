package com.ddevs.edgeify.views

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.ddevs.edgeify.MainViewModel
import com.ddevs.edgeify.R
import com.ddevs.edgeify.databinding.FragmentResultBinding
import com.google.android.material.snackbar.Snackbar


class ResultFragment : Fragment() {
    val viewModel:MainViewModel by lazy {
        ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
    }
    lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_result, container, false)
        viewModel.resultUri.observe(viewLifecycleOwner) {
            if (it != null) {
                Glide.with(requireContext()).load(it.first).into(binding.imageView)
                Glide.with(requireContext()).load(it.second).into(binding.imageView2)
                binding.url.text = it.second
            }
        }

        binding.copyBtn.setOnClickListener{
            val clipboard: ClipboardManager? =
                requireActivity().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager?
            val clip = ClipData.newPlainText("url", binding.url.text)
            clipboard?.setPrimaryClip(clip)
            Snackbar.make(binding.root,"URL copied!", Snackbar.LENGTH_SHORT).show()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner,object:
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                viewModel.reset()
                findNavController().navigateUp()
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.reset()
    }
}