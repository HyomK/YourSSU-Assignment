package com.assignment2

import androidx.databinding.BindingAdapter
import com.assignment2.customview.SSUInputField
import com.google.android.material.textfield.TextInputEditText

@BindingAdapter("pwdState")
fun SSUInputField.bindState( state: String ){
  this.setState(state)
}
