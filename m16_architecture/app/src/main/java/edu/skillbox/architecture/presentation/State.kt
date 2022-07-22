package edu.skillbox.architecture.presentation

sealed class State {
    object Loading: State()
    object Success: State()
}
