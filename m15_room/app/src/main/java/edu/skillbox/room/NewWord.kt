package edu.skillbox.room

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

data class NewWord(
    @PrimaryKey
    @ColumnInfo(name = "word")
    val word: String? = null,
    @ColumnInfo(name = "repetition")
    val repetition: Int
)
