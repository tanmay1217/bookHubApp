package com.sumitgupta.bookhub.database

import androidx.room.*

@Dao  //annotation
interface BookDao {

    @Insert
    fun insertBook(bookEntity: BookEntity)

    @Delete
    fun deleteBook(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAllBooks():List<BookEntity>

    @Query("SELECT * FROM books WHERE book_id= :bookId")  // here : before bookId signifies that bookId value will come from the function just below
    fun getBookById(bookId:String):BookEntity
}