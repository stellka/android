package edu.skillbox.mvvp.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import edu.skillbox.mvvp.State
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

private const val TAG = "MainViewModel"

class MainViewModel() : ViewModel() {

    private val _state = MutableStateFlow<State>(State.Error)
    val state = _state.asStateFlow()

    private val _error = Channel<String>()
    val error = _error.receiveAsFlow()


    fun onSignInClick(login: String) {
        viewModelScope.launch {
                _state.value = State.Loading
                delay(5000)
                _state.value = State.Success
        }
    }

    fun onEditText(searchString: String) {
        viewModelScope.launch {
            if (searchString.length < 3 || searchString.isEmpty()) {
                _state.value = State.Error
                _error.send("Not valid")
            }else {
                _state.value = State.Ready
            }
        }
    }
}