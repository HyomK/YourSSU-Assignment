package com.assignment1.ui.register

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.assignment1.MainActivity
import com.assignment1.R
import com.assignment1.data.database.User
import com.assignment1.data.database.UserDatabase
import com.assignment1.databinding.FragmentRegisterBinding
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class RegisterFragment : Fragment() {
    private var _binding : FragmentRegisterBinding? = null

    private val binding get() = _binding!!

    private val registerViewModel by lazy {
        ViewModelProvider(activity as MainActivity).get(RegisterViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_register, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = registerViewModel
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setObserver()
    }

    private fun setObserver(){
        registerViewModel.viewEvents.
            flowWithLifecycle(lifecycle)
            .onEach(::handleEvents)
            .launchIn(lifecycleScope)

        registerViewModel.uiState
            .flowWithLifecycle(lifecycle)
            .onEach { binding.uiState=it }
            .launchIn(lifecycleScope)
    }

    private fun handleEvents(events : List<RegisterViewEvent>){
        events.firstOrNull()?.let{
            event ->
                when(event){
                    is RegisterViewEvent.Success->{
                        (context as MainActivity).run {
                            hideKeyBoard(binding.frRegisterPhoneEt)
                            Toast.makeText(this,"등록되었습니다",Toast.LENGTH_SHORT).show()
                        }
                    }
                    is RegisterViewEvent.Failure->{
                        Toast.makeText((context as MainActivity),event.msg,Toast.LENGTH_SHORT).show()
                    }
                }
            registerViewModel.consumeEvent(event)
        }
    }

    private fun Context.hideKeyBoard(edittext :EditText){
        val inputMethodManager = this.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(edittext.windowToken,   0)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}