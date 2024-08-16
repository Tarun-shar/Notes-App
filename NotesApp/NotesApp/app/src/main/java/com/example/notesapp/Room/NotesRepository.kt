package com.example.notesapp.Room

import androidx.lifecycle.LiveData
import com.example.notesapp.Models.Note

class NotesRepository(private val notesDao: NotesDao) {

    val allNotes : LiveData<List<Note>> = notesDao.getAllNotes()

    suspend fun insert(note: Note){
        notesDao.insertNotes(note)
    }

    suspend fun delete(note: Note){
        notesDao.deleteNotes(note)
    }

    suspend fun update(note: Note){
        notesDao.updateNotes(note.id,note.title,note.note)
    }
}