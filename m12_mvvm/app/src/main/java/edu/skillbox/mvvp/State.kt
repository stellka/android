package edu.skillbox.mvvp

sealed class State{
    object Loading: State()
    object Success: State()
    object Error : State()
    object Ready : State()
}

