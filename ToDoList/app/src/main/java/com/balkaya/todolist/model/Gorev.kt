package com.balkaya.todolist.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
 class Gorev(

    @ColumnInfo(name="dbGorevBaslik")
    val gorevBaslik : String,
    @ColumnInfo(name="dbGorevKonu")
    val gorevKonu:String,
    @ColumnInfo(name="dbYapilmaDurumu")
    val yapilmaDurumu:Boolean,
    @ColumnInfo(name="dbSonTarih")
    val sonTarih:String,
    @ColumnInfo(name ="dbGorevAciklama")
    val aciklama:String ) {

   @PrimaryKey(autoGenerate = true)
   var id=0




}