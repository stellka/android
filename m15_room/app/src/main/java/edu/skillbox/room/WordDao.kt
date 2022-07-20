package edu.skillbox.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM Word LIMIT 5")
    fun getAll() : Flow<List<Word>>

    @Query("SELECT * FROM Word")
    fun getAllForDelete() : Flow<List<Word>>

    @Insert(entity = Word::class)
    suspend fun insert(word: Word)

    @Delete
    suspend fun delete(word: Word)

    @Query("UPDATE Word SET repetition = repetition + 1 WHERE word = :name")
    suspend fun update(name: String)
}