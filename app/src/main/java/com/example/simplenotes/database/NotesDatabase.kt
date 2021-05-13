package com.example.simplenotes.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [Note::class], version = 6, exportSchema = false)
abstract class NotesDatabase : RoomDatabase() {
    abstract val notesDao : NotesDao

    companion object{
        @Volatile
        private var INSTANCE : NotesDatabase? = null

        fun getInstance(context: Context) : NotesDatabase{
            synchronized(this){
                var instance = INSTANCE
                if (instance == null){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        NotesDatabase::class.java,
                        "my_notes_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}