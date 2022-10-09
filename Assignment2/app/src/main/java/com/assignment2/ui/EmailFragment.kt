package com.assignment2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.assignment2.R
import com.assignment2.databinding.FragmentEmailBinding

class EmailFragment : Fragment() {

    private var _binding : FragmentEmailBinding? = null

    private val binding get() = _binding!!

    private val viewModel by activityViewModels<EmailViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_email, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        binding.frEmailNextBtn.isEnabled = false
        binding.inputField.getEditText?.addTextChangedListener {  viewModel.handleEmail(it) }
        binding.frEmailNextBtn.setOnClickListener {
            findNavController().navigate(R.id.action_emailFragment_to_passwordFragment)
        }
    }
}