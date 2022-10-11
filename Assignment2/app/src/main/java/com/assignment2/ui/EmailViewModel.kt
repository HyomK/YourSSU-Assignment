package com.assignment2.ui

import android.text.Editable
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.assignment2.data.AccountRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EmailViewModel@Inject constructor(private val repository: AccountRepository): ViewModel(){
    private val _uiState = MutableLiveData(EmailUiState(onEmailChanged=::handleEmail))
    val uiState get() = _uiState

    fun handleEmail(editText: Editable?){
        _uiState.postValue(_uiState.value?.copy(
            input = editText.toString().replace(" ","")
        ))
    }

    fun onClickNext(){
        repository.setEmail(uiState.value!!.input!!)
    }

    data class EmailUiState(
        val input : String? = "",
        val onEmailChanged:(Editable?)->Unit,
    ){
        val isEnabled = !input.isNullOrBlank()
    }
}