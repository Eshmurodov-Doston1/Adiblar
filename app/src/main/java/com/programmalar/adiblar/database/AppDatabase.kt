package com.programmalar.adiblar.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.programmalar.adiblar.dao.AppDao
import com.programmalar.adiblar.entitiy.WriterEntity

@Database(entities = [WriterEntity::class],version = 1)
abstract class AppDatabase:RoomDatabase() {
    abstract fun addDao():AppDao

    companion object{
        private var instance:AppDatabase?=null
        @Synchronized
        fun getInstance(context: Context):AppDatabase{
            if (instance==null){
                instance = Room.databaseBuilder(context,AppDatabase::class.java,"database.db")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return instance!!
        }
    }
}