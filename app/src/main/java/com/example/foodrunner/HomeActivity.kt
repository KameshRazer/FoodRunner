package com.example.foodrunner

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.foodrunner.ui.FAQs.FAQsFragment
import com.example.foodrunner.ui.favouriteRestaurants.FavouriteFragment
import com.example.foodrunner.ui.home.HomeFragment
import com.example.foodrunner.ui.myProfile.MyProfileFragment
import com.example.foodrunner.ui.orderHistory.OrderHistoryFragment
import com.google.android.material.navigation.NavigationView


class HomeActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout : DrawerLayout
    private lateinit var navView : NavigationView
    private  var navPosition = 0

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        drawerLayout= findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val head :View = navView.getHeaderView(0)
        val name :TextView = head.findViewById(R.id.nav_header_name)
        val mobile :TextView = head.findViewById(R.id.nav_header_mobile_no)

        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Alert")
        alertDialog.setMessage("Are you sure?")
        alertDialog.setPositiveButton("Yes"){ _, _ ->
            val logInfo = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
            logInfo.edit().clear().apply()
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
        alertDialog.setNeutralButton("No"){ _, _ ->  navView.setCheckedItem(navPosition)}


        val logInfo = getSharedPreferences("LoginInfo", Context.MODE_PRIVATE)
        name.text = logInfo.getString("name","error")
        mobile.text = logInfo.getString("mobileNo","error")

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_myProfile, R.id.nav_favouriteRestaurants, R.id.nav_FAQs,R.id.nav_logout,R.id.nav_orderHistory
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
//        navView.setupWithNavController(navController)

        setFragment(HomeFragment())
        navView.setNavigationItemSelectedListener { item ->
            when(item.itemId){
                R.id.nav_home -> {
                    setFragment(HomeFragment())
                    navPosition = item.itemId
                }
                R.id.nav_myProfile -> {
                    setFragment(MyProfileFragment())
                    navPosition = item.itemId

                }
                R.id.nav_favouriteRestaurants -> {
                    setFragment(FavouriteFragment())
                    navPosition = item.itemId

                }
                R.id.nav_orderHistory -> {
                    setFragment(OrderHistoryFragment())
                    navPosition = item.itemId

                }
                R.id.nav_FAQs -> {
                    setFragment(FAQsFragment())
                    navPosition = item.itemId

                }
                R.id.nav_logout -> {
                    val logout = alertDialog.create()
                    logout.setCancelable(false)
                    logout.show()
                }
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setFragment(fragment :Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment,fragment)
            .commit()
    }

    @SuppressLint("WrongConstant")
    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.START))
            drawerLayout.closeDrawer(Gravity.START)

        if(navPosition == R.id.nav_home)
            finish()
        else{
            navPosition = R.id.nav_home
            setFragment(HomeFragment())
            navView.setCheckedItem(navPosition)
        }

    }

}
