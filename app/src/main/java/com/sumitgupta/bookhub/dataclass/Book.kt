package com.sumitgupta.bookhub.dataclass

data class Book(
    //earlier we are using  this data class below members to create data list in dashboard fragment and passing it to the adapter class
   /* val bookName:String,
    val bookAuthor:String,
    val bookCost:String,
    val bookRating:String,
    val bookImage:Int

    */

    // now we are requesting the data for our bookhub app through json object so the data present on the server database , or
    // the order in which the data of book details present in the database ,
    // we have to write the variable name below as present in the database and also in the same order of the database
    // currently the order of variables in the  database given by internshala  is as  written below
    val bookId:String,
    val bookName:String,
    val bookAuthor:String,
    val bookRating:String,
    val bookPrice:String,
    val bookImage: String


)

