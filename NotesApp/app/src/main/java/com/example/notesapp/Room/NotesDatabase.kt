package com.example.notesapp.Room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.notesapp.Models.Note
import com.example.notesapp.utilities.DATABASE_NAME

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class NotesDatabase:RoomDatabase() {

    abstract fun getNoteDao(): NotesDao

    companion object{

        @Volatile
        private var INSTANCE: NotesDatabase? = null

        fun getDatabase(context: Context): NotesDatabase {
            if (INSTANCE == null){
                synchronized(this){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        NotesDatabase::class.java,
                        DATABASE_NAME)
                        .build()
                }
            }
            return INSTANCE!!
        }
    }
}