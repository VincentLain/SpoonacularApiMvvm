package com.vincent.lain.data.db

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.vincent.lain.data.model.Menu

@Database(entities = [Menu::class], version = 1)
abstract class MenuDatabase : RoomDatabase() {
    abstract fun menuDao(): MenuDao

    companion object {
        private val lock = Any()
        private const val DB_NAME = "MenuDatabase"
        private var INSTANCE: MenuDatabase? = null

        fun getInstance(application: Application): MenuDatabase {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(application, MenuDatabase::class.java, DB_NAME)
                            .build()
                }
            }
            return INSTANCE!!
        }
    }

}