package com.sumitgupta.bookhub.fragment

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.sumitgupta.bookhub.R
import com.sumitgupta.bookhub.adapter.DahboardRecyclerAdapter
import com.sumitgupta.bookhub.dataclass.Book
import com.sumitgupta.bookhub.util.ConnectionManager
import org.json.JSONException
import org.json.JSONObject
import java.util.*
import kotlin.Comparator
import kotlin.collections.HashMap


class DashboardFragment : Fragment() {

    lateinit var recyclerDashboard:RecyclerView



    lateinit var layoutManager:RecyclerView.LayoutManager

    lateinit var recyclerAdapter:DahboardRecyclerAdapter

    lateinit var progressLayout:RelativeLayout
    lateinit var progressBar:ProgressBar

    val bookInfoList = arrayListOf<Book>()

    var ratingComparator= Comparator<Book>{book1,book2 ->

        if (book1.bookRating.compareTo(book2.bookRating,true)==0){
            // sort according to name alphabetically  if same rating
            book1.bookName.compareTo(book2.bookName,true)
        }else{
        book1.bookRating.compareTo(book2.bookRating,true)
        }
    }

    // we commented below because earlier we are using static list through data class , now we are fetching the data from the api server
    // through jsonRequestObject (done in onCreateView method)
    /*val bookInfoList = arrayListOf<Book>(

        Book("P.S. I love You", "Cecelia Ahern", "Rs. 299", "4.5", R.drawable.ps_ily),
        Book("The Great Gatsby", "F. Scott Fitzgerald", "Rs. 399", "4.1", R.drawable.great_gatsby),
        Book("Anna Karenina", "Leo Tolstoy", "Rs. 199", "4.3", R.drawable.anna_kare),
        Book("Madame Bovary", "Gustave Flaubert", "Rs. 500", "4.0", R.drawable.madame),
        Book("War and Peace", "Leo Tolstoy", "Rs. 249", "4.8", R.drawable.war_and_peace),
        Book("Lolita", "Vladimir Nabokov", "Rs. 349", "3.9", R.drawable.lolita),
        Book("Middlemarch", "George Eliot", "Rs. 599", "4.2", R.drawable.middlemarch),
        Book("The Adventures of Huckleberry Finn", "Mark Twain", "Rs. 699", "4.5", R.drawable.adventures_finn),
        Book("Moby-Dick", "Herman Melville", "Rs. 499", "4.5", R.drawable.moby_dick),
        Book("The Lord of the Rings", "J.R.R Tolkien", "Rs. 749", "5.0", R.drawable.lord_of_rings)

    )

     */




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_dashboard, container, false)

        setHasOptionsMenu(true)   // this method is used only to add menu to our fragment , but to add menu in an activity the compiler
        // automatically add the menu item in activity by onCreateOptionsMenu() method


        recyclerDashboard=view.findViewById(R.id.recyclerDashboard)

        layoutManager=LinearLayoutManager(activity)

        progressLayout=view.findViewById(R.id.progressLayout)
        progressBar=view.findViewById(R.id.progressBar)

        progressLayout.visibility=View.VISIBLE





       val queue= Volley.newRequestQueue(activity as Context)
        val url="http://13.235.250.119/v1/book/fetch_books/"

        if(ConnectionManager().checkConnectivity(activity as Context)){

            val jsonObjectRequest = object : JsonObjectRequest(Request.Method.GET,url,null,Response.Listener{

                // here we will handle the response
               // println("Response is $it ") // to get response( data items) in logcat window

                try {
                    progressLayout.visibility=View.GONE

                    val success = it.getBoolean("success")


                    if(success){

                        val data=it.getJSONArray("data")
                        for (i in 0 until data.length()){
                            val bookJsonObject=data.getJSONObject(i)
                            val bookObject=Book(
                                bookJsonObject.getString("book_id"),
                                bookJsonObject.getString("name"),
                                bookJsonObject.getString("author"),
                                bookJsonObject.getString("rating"),
                                bookJsonObject.getString("price"),
                                bookJsonObject.getString("image")

                            )

                            bookInfoList.add(bookObject)

                            recyclerAdapter= DahboardRecyclerAdapter(activity as Context,bookInfoList)

                            recyclerDashboard.adapter=recyclerAdapter

                            recyclerDashboard.layoutManager=layoutManager

                            // this below code give the partition line to the recycler view items
                           /* recyclerDashboard.addItemDecoration(
                                DividerItemDecoration(
                                    recyclerDashboard.context,
                                    (layoutManager as LinearLayoutManager).orientation
                                )
                            )

                            */
                        }
                    }
                    else{
                        Toast.makeText(activity as Context,"Some error occured",Toast.LENGTH_SHORT).show()
                    }
                }catch (e:JSONException){
                    Toast.makeText(activity as Context,"Some unexpected error occured!!!",Toast.LENGTH_SHORT).show()
                }


            },Response.ErrorListener {
               // wi will handle the volley error here
               // println("Error is $it ")
                if (activity!=null){
                Toast.makeText(activity as Context,"Volley error occured!!!",Toast.LENGTH_SHORT).show()
                }
                    }
                ){
                override fun getHeaders(): MutableMap<String, String> {
                    val headers = HashMap<String, String>()
                    headers["Content-type"] = "application/json"
                    headers["token"] = "01e97dd80654c9"
                    return headers
                }
            }

            queue.add(jsonObjectRequest)

        }else{
            // internet not available
            val dialog= AlertDialog.Builder(activity as Context)
            dialog.setTitle("Error")
            dialog.setMessage("Internet connection not found")
            dialog.setPositiveButton("Open Setting"){text,listener ->

                val settingIntent=Intent(Settings.ACTION_WIRELESS_SETTINGS)
                startActivity(settingIntent)
                activity?.finish()
            }
            dialog.setNegativeButton("Exit"){text,listener ->

                ActivityCompat.finishAffinity(activity as Activity)
            }
            dialog.create()
            dialog.show()
        }


        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {  // this method is used to add the menu item to the appbar
        inflater?.inflate(R.menu.dashboard_menu,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        val id=item?.itemId
        if(id==R.id.action_sort){
            Collections.sort(bookInfoList,ratingComparator)
            bookInfoList.reverse()  // it will sort in descending order
        }
        recyclerAdapter.notifyDataSetChanged()

        return super.onOptionsItemSelected(item)
    }
}
