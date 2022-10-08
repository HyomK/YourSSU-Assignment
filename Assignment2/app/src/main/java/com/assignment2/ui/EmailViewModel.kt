package com.assignment2.ui

import android.text.Editable
import android.util.Log
import android.widget.EditText
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class EmailViewModel : ViewModel() , Serializable {
    private val _uiState = MutableLiveData(EmailUiState(onEmailChanged=::handleInput))
    val uistate get() = _uiState

    fun handleInput(editText: Editable?){
        _uiState.postValue(_uiState.value?.copy(input = editText.toString()))
    }

    data class EmailUiState(
        val input : String? = "",
        val onEmailChanged:(Editable?)->Unit
    ){
        val isEnabled = !input.isNullOrBlank()
    }
}