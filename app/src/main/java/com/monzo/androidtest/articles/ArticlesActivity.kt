package com.monzo.androidtest.articles

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R

class ArticlesActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapterByWeek: ArticleByWeekAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

        setSupportActionBar(toolbar)
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        // val navController = findNavController(R.id.nav_graph)
        // appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)
//        NavigationUI.setupActionBarWithNavController(this, navController)

//
//        viewModel = HeadlinesApp.from(applicationContext).inject(this)
//        adapterByWeek = ArticleByWeekAdapter(this,)
//        recyclerView.layoutManager = LinearLayoutManager(this)
//        recyclerView.adapter = adapterByWeek
//
//        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }
//
//        viewModel.state.observe(this) { state ->
//            swipeRefreshLayout.isRefreshing = state.refreshing
//           // state.articlesByWeeks = emptyList()
//            adapterByWeek.showArticles(state.articlesByWeeks)
//        }
    }
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration)
                || super.onSupportNavigateUp()
    }
}
