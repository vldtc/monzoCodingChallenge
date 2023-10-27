package com.monzo.androidtest.articles

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R

class ArticlesActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapterByWeek: ArticleByWeekAdapter

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.articles_swiperefreshlayout)
        val recyclerView = findViewById<RecyclerView>(R.id.articles_by_week_recyclerview)

//        val navController = Navigation.findNavController(this, R.id.nav_graph)
//        NavigationUI.setupActionBarWithNavController(this, navController)


        setSupportActionBar(toolbar)

        viewModel = HeadlinesApp.from(applicationContext).inject(this)
        adapterByWeek = ArticleByWeekAdapter(this,)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterByWeek

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(this) { state ->
            swipeRefreshLayout.isRefreshing = state.refreshing
           // state.articlesByWeeks = emptyList()
            adapterByWeek.showArticles(state.articlesByWeeks)
        }
    }
}
