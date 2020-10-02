package com.sumitgupta.bookhub.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [BookEntity::class],version = 1)
abstract class BookDatabase :RoomDatabase(){

    // all the functions that we will perform on the data will be done by Dao and to do that ,
    // we write below code
    abstract fun bookDao():BookDao
    // this function serves as a doorway to use all Dao functions like insert , delete

}
