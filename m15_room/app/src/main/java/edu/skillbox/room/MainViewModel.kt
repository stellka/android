package edu.skillbox.room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {
    val allWords = this.wordDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun onAddBtn(edit: String) {
        viewModelScope.launch {
            wordDao.insert(
                Word(
                    word = edit,
                    repetition = 0
                )
            )
        }
    }

    fun isWordHere(str: String) {
        viewModelScope.launch {
            wordDao.isWordHere(str)
        }
    }


    fun onUpdate(str: String) {
        viewModelScope.launch {
            wordDao.update(str)
        }
    }

    fun onDeleteBtn() {
        viewModelScope.launch {
            wordDao.deleteAll()
        }
    }
}