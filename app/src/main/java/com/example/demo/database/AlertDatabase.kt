package com.example.demo.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.demo.models.Alert

@Database(entities = [Alert::class], version = 1, exportSchema = false)
abstract class AlertDatabase : RoomDatabase(){
    abstract fun userDao():AlertDao

}