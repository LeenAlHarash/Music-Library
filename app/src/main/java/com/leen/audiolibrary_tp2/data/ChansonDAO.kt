package com.leen.audiolibrary_tp2.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface ChansonDAO {

    @Insert
    suspend fun insert(chanson : Chanson)

    @Update
    suspend fun update(chanson: Chanson)

    @Delete
    suspend fun delete(chanson: Chanson)

    @Transaction // Execution de plusieurs requetes
    @Query("SELECT * FROM Chanson")
    fun getAll(): LiveData<List<ChansonAvecArtisteGenre>>
}