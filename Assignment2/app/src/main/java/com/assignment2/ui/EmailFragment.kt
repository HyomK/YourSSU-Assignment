package com.assignment2.ui

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import com.assignment2.R
import com.assignment2.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {

    private var _binding : FragmentEmailBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<EmailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
        binding.frEmailNextBtn.isEnabled = false
        return binding.root
    }

}