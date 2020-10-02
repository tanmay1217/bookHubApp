package com.sumitgupta.bookhub.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.FrameLayout
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar

import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import com.sumitgupta.bookhub.*
import com.sumitgupta.bookhub.fragment.AboutAppFragment
import com.sumitgupta.bookhub.fragment.DashboardFragment
import com.sumitgupta.bookhub.fragment.FavouritesFragment
import com.sumitgupta.bookhub.fragment.ProfileFragment

class MainActivity : AppCompatActivity() {

    lateinit var  drawerLayout:DrawerLayout
    lateinit var coordinateLayout:CoordinatorLayout
    lateinit var toolbar:Toolbar
    lateinit var frameLayout: FrameLayout
    lateinit var navigationView: NavigationView

    var previousMenuItem:MenuItem ?=null

    // before writing the kotlin code add the dependency to app implementation 'com.android.support:design:28.0.0'

    /*
     navigation drawer have two parts: menu drawer and drawer header, so we have to create the two separate xml layout of menu and header
     and adding these two layouts to NavigationView in main_activity.xml
     */


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        drawerLayout=findViewById(R.id.drawerLayout)
        coordinateLayout=findViewById(R.id.coordinatorLayout)
        toolbar=findViewById(R.id.toolbar)
        frameLayout=findViewById(R.id.frame)
        navigationView=findViewById(R.id.navigationView)

        setUpToolbar()  // step 1 of 7 Navigation Drawer, calling toolbar making function


        //we have created fragments for each menu items of navigation drawer like dashboard fragment, favourites fragment etc.
        // calling function openDashboard , bcoz we have to set the dashboard fragment as default screen while open the app instead of main_activity.xml
        openDashboard()




        // step 3 of 7 Navigation Drawer, creating toggle for drawer layout
        val actionBarDrawerToggle=ActionBarDrawerToggle(this@MainActivity, drawerLayout,
            R.string.open_drawer,
            R.string.close_drawer
        )

        // step 4 of 7 Navigation Drawer, adding addDrawer listener to toggle
        drawerLayout.addDrawerListener(actionBarDrawerToggle) // this line of code is making the hamburger like function
        actionBarDrawerToggle.syncState() // it will do, when the drawer is outside we will press the hamburger icon then the drawer will come in and icon will change into back arrow icon



        // step 7 of 7 Navigation Drawer, adding click listeners to menu items in navigation drawer
        navigationView.setNavigationItemSelectedListener {

            // these below 5 lines will highlighted the currently selected item in the navigation drawer to identify which screen is currently active
            if (previousMenuItem!=null){
                previousMenuItem?.isChecked=false
            }
            it.isCheckable=true
            it.isChecked=true
            previousMenuItem=it



            when(it.itemId){      // it will give the id of currently selected menu item

                R.id.dashboard ->{   // dashboard id is of the id of the menu_drawer.xml dashboard item
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            DashboardFragment()
                        )
                        //.addToBackStack("Dashboard")  // adding Dashboard fragment to stack so when back button is pressed activity will bot open up but the last fragment in stack will openup
                        .commit()

                    supportActionBar?.title="Dashboard"   // this line will set the title of toolbar equal to Dashboard whe  Dashboard fragment will openup
                    drawerLayout.closeDrawers()
                }
                R.id.favourites ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            FavouritesFragment()
                        )
                        .commit()

                    supportActionBar?.title="Favourites"
                    drawerLayout.closeDrawers()
                }
                R.id.profile ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            ProfileFragment()
                        )
                        .commit()

                    supportActionBar?.title="Profile"
                    drawerLayout.closeDrawers()
                }
                R.id.aboutApp ->{
                    supportFragmentManager.beginTransaction()
                        .replace(
                            R.id.frame,
                            AboutAppFragment()
                        )
                        .commit()

                    supportActionBar?.title="About App"
                    drawerLayout.closeDrawers()
                }
            }
            return@setNavigationItemSelectedListener true
        }
    }

    // step 2 of 7  Navigation Drawer, creating toolbar, but first change the app theme in styles.xml to no action bar
   fun setUpToolbar(){
        setSupportActionBar(toolbar)    // import androidx.appcompat.widget.Toolbar     use this line at top if give error
        supportActionBar?.title="Toolbar Title"
        // these above two lines are enough to create toolbar with some title

        //toolbar have hidden home button at top left corner to enable it, write below two lines of code
        //the home button is also known as hamburger button
       supportActionBar?.setHomeButtonEnabled(true)
       supportActionBar?.setDisplayHomeAsUpEnabled(true)

     }

    // step 5 of 7 Navigation Drawer, create this below inbuilt function to add the action to the actionbar or toolbar, it will help to generate id when click on menu item of drawer
      override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id=item.itemId

        // step 6 of 7  Navigation Drawer, to make the drawer slide from left side
        if(id==android.R.id.home){    // the line inside the if means that the drawer is closed now
            drawerLayout.openDrawer(GravityCompat.START) // set gravity to start  to make the drawer slide from left side
        }

        return super.onOptionsItemSelected(item)
      }


      fun openDashboard(){
        val fragment= DashboardFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.frame,fragment)
            .commit()
        supportActionBar?.title="Dashboard"
          navigationView.setCheckedItem(R.id.dashboard)

      }

    // below function is used to provide better functionality to back press button as previously the issue i.e. when press back fragment
    // changed but the title of the app dosent changed so we override the onBackPressed() function , so don't use the addToBackStack()
    // function anywhere above as we override the onBackPressed() function.
      override fun onBackPressed() {
        val frag=supportFragmentManager.findFragmentById(R.id.frame)

        when(frag){
            !is DashboardFragment -> openDashboard()

            else->super.onBackPressed()  // this line means if the dashboard fragment is currently opened up then  pressing back button will
            // simply exit the app or will return to the previous activity
        }

      }

}
