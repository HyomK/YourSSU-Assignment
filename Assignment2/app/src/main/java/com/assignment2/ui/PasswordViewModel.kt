package com.assignment2.ui

import android.text.Editable
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.assignment2.data.AccountRepository
import com.assignment2.util.RegexConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasswordViewModel @Inject constructor(private val repository: AccountRepository): ViewModel() {

    private val _uiState = MutableLiveData(PasswordUiState(onEmailChanged=::handlePassword))
    val uiState get() = _uiState

    private var passwordValidationJob: Job? = null
    fun handlePassword(editText: Editable?){
        passwordValidationJob?.cancel()

        passwordValidationJob = viewModelScope.launch {
            delay(300L)
            _uiState.postValue(_uiState.value?.copy(
                input = editText.toString().replace(" ",""))
            )
        }
    }

    fun onClickNext(){
        repository.setPassword(uiState.value!!.input!!)
    }

    data class PasswordUiState(
        val input : String? = "",
        val onEmailChanged:(Editable?)->Unit
    ){
        val isEnabled = !input.isNullOrBlank()&& input.matches(RegexConstants.PWD_REGEX)
        val state = if(input.isNullOrBlank()) "" else if(isEnabled) "success" else "error"
    }
}