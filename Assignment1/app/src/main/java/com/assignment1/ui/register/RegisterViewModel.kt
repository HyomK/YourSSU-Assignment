package com.assignment1.ui.register

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RegisterViewModel(): ViewModel() {

    private val _viewEvents: MutableStateFlow<List<RegisterViewEvent>> = MutableStateFlow(emptyList())
    val viewEvents = _viewEvents.asStateFlow()

    fun addProfile(){
        _viewEvents.update{ it + RegisterViewEvent.Success }
    }

    private fun consumeEvent(viewEvent: RegisterViewEvent) {
        _viewEvents.update{it - viewEvent}
    }
}

sealed interface RegisterViewEvent {
    object Success : RegisterViewEvent
    object Failure : RegisterViewEvent
}