package edu.skillbox.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {
    @Query("SELECT * FROM Word LIMIT 5")
    fun getAll() : Flow<List<Word>>

    @Query("SELECT * FROM Word")
    fun getAllForDelete() : Flow<List<Word>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(word: Word)

    @Query("DELETE FROM Word")
    suspend fun deleteAll()

    @Query("UPDATE Word SET repetition = repetition + 1 WHERE word = :name")
    suspend fun update(name: String)

    @Query("SELECT * FROM Word WHERE word IS :name")
    suspend fun isWordHere(name: String) : Word
}