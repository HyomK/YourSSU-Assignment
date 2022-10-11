package com.assignment2.ui

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.Serializable

class EmailViewModel : ViewModel() , Serializable {
    private val _uiState = MutableLiveData(EmailUiState(onEmailChanged=::handleEmail))
    val uistate get() = _uiState

    fun handleEmail(editText: Editable?){
        _uiState.postValue(_uiState.value?.copy(
            input = editText.toString().replace(" ","")
        ))
    }

    data class EmailUiState(
        val input : String? = "",
        val onEmailChanged:(Editable?)->Unit
    ){
        val isEnabled = !input.isNullOrBlank()
    }
}