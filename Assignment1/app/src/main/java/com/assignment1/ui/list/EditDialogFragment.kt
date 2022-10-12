package com.assignment1.ui.list

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Point
import android.os.Bundle
import android.telephony.PhoneNumberFormattingTextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.assignment1.MainActivity
import com.assignment1.R
import com.assignment1.data.database.User
import com.assignment1.databinding.FragmentEditDialogBinding
import com.assignment1.databinding.FragmentListBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update

class EditDialogFragment : DialogFragment() {

    private var _binding : FragmentEditDialogBinding? = null

    private val binding get() = _binding!!

    private val viewModel by viewModels<EditDialogViewModel>()
    private val listViewModel by activityViewModels<ListViewModel>()

    override fun onResume() {
        super.onResume()
        val display = windowManager?.defaultDisplay
        val size = Point()
        display?.getSize(size)

        val params : ViewGroup.LayoutParams? = dialog?.window?.attributes
        val deviceWidth = size.x
        params?.width = (deviceWidth * 0.9).toInt()
        dialog?.window?.attributes = params as WindowManager.LayoutParams
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_edit_dialog, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        setListener()

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            binding.frEditUsername.setText(it.getParcelable<User>("user")?.name)
        }

        viewModel.uiState.flowWithLifecycle(lifecycle)
            .onEach { binding.uiState=it }
            .launchIn(lifecycleScope)
    }

    private fun setListener(){
        binding.frEditNegativeBtn.setOnClickListener {
            dialog?.dismiss()
        }
        binding.frEditPositiveBtn.setOnClickListener {
            viewModel.updateUser()
            listViewModel._uiState.update { it.copy(search = viewModel.uiState.value.name ) }
            Toast.makeText( (activity as MainActivity),"수정되었습니다.", Toast.LENGTH_SHORT).show()
            dialog?.dismiss()
        }
        binding.frEditPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }


    companion object{
        fun newInstance(context: Context, user: User):EditDialogFragment{
            val f = EditDialogFragment()
            windowManager= context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val args = Bundle().apply { putParcelable("user", user) }
            f.arguments=args
            return f
        }
        var windowManager : WindowManager ?=null
    }
}