package com.balkaya.todolist.roomdb

import androidx.room.Database
import androidx.room.RoomDatabase
import com.balkaya.todolist.model.Gorev

@Database(entities = [Gorev::class], version = 1)
abstract class GorevDatabase : RoomDatabase(){
    abstract fun gorevDAO():GorevDAO
}