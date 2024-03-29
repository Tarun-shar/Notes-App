package com.example.notesapp.Room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.notesapp.Models.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNotes(note: Note)

    @Query("UPDATE notes_table Set title = :title, note = :note WHERE id = :id")
    suspend fun updateNotes(id : Int?, title : String?, note : String?)

    @Delete
    suspend fun deleteNotes(note: Note)

    @Query("Select * FROM notes_table order by id ASC")
    fun getAllNotes() : LiveData<List<Note>>
}