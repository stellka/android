package edu.skillbox.attraction

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val photoDao: PhotoDao) : ViewModel() {

    fun onTakePhotoBtn(route: String) {
        viewModelScope.launch {
            photoDao.insert(
                Photo(route = route)
            )
        }
    }
}