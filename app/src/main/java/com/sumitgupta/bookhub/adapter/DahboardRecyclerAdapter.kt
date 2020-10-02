package com.sumitgupta.bookhub.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.sumitgupta.bookhub.R
import com.sumitgupta.bookhub.activity.DescriptionActivity
import com.sumitgupta.bookhub.dataclass.Book
import kotlinx.android.synthetic.main.recycler_dashboard_single_view.view.*

class DahboardRecyclerAdapter(val context: Context,val itemList: ArrayList<Book>): RecyclerView.Adapter<DahboardRecyclerAdapter.DashboardViewHolder>() {
   // the data to this adapter class will come from the dashboard fragment i.e. we create the object of the DahboardRecyclerAdapter() and pass the data
    // inside () so it will initialize through constructor

    // the viewHolder class will always be made inside the adapter class
    class DashboardViewHolder(view:View):RecyclerView.ViewHolder(view){

        val txtBookName:TextView=view.findViewById(R.id.txtBookName)   //holding the view of recycler_dashboard_single_view.xml  file
        val txtBookAuthor:TextView=view.findViewById(R.id.txtBookAuthor)
        val txtBookPrice:TextView=view.findViewById(R.id.txtBookPrice)
        val txtBookRating:TextView=view.findViewById(R.id.txtBookRating)
        val imgBookImage:ImageView=view.findViewById(R.id.imgBookImage)

        val llContent:LinearLayout=view.findViewById(R.id.llContent)   // initializing linear layout of recycler_dashboard_single_view.xml to make the layout clickable
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DashboardViewHolder {
        val view=LayoutInflater.from(parent.context).inflate(R.layout.recycler_dashboard_single_view,parent,false)
        return DashboardViewHolder(view)
    }

    override fun getItemCount(): Int {
       return itemList.size
    }

    override fun onBindViewHolder(holder: DashboardViewHolder, position: Int) {
        val book=itemList[position]
        holder.txtBookName.text=book.bookName
        holder.txtBookAuthor.text=book.bookAuthor
        holder.txtBookPrice.text=book.bookPrice
        holder.txtBookRating.text=book.bookRating
        //holder.imgBookImage.setImageResource(book.bookImage)
        Picasso.get().load(book.bookImage).error(R.drawable.default_book_cover).into(holder.imgBookImage)

        holder.llContent.setOnClickListener{
           // Toast.makeText(context,"clicked on ${holder.txtBookName.text}",Toast.LENGTH_SHORT).show()
            val intent=Intent(context,DescriptionActivity::class.java)
            intent.putExtra("book_id",book.bookId)
            context.startActivity(intent)

        }

    }
}