package com.balkaya.todolist.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.balkaya.todolist.model.Gorev
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable

@Dao
interface GorevDAO {

    @Query("SELECT * FROM Gorev")
    fun getAll(): Flowable<List<Gorev>>

    @Query("SELECT * FROM Gorev WHERE id= :id")
    fun findById(id:Int):Flowable<Gorev>

    @Insert
    fun insert(gorev:Gorev):Completable

    @Delete
    fun delete(gorev: Gorev):Completable

    @Update
    fun update(gorev:Gorev):Completable
}