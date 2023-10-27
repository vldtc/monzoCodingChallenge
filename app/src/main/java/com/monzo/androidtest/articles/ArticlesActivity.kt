package com.monzo.androidtest.articles

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.ui.AppBarConfiguration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article
import io.reactivex.Single

class ArticlesActivity : AppCompatActivity() {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapterByWeek: ArticleByWeekAdapter
    private lateinit var appBarConfiguration: AppBarConfiguration

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_list)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.articles_swiperefreshlayout)
        val recyclerView = findViewById<RecyclerView>(R.id.articles_by_week_recyclerview)

        setSupportActionBar(toolbar)

        //val navController = findNavController(R.id.nav_graph)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        //setupActionBarWithNavController(navController, appBarConfiguration)
//        NavigationUI.setupActionBarWithNavController(this, navController)


        viewModel = HeadlinesApp.from(applicationContext).inject(this)
        adapterByWeek = ArticleByWeekAdapter(this,)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapterByWeek

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(this) { state ->
            val listPlusFavorites = mutableListOf<List<Article>>()
            val favoriteList = mutableListOf<Article>()
            if(state.favorites.isNotEmpty()){
                for(favorite in state.favorites){
                    val favorite = viewModel.getFavorite(favorite.url)
                    favoriteList.add(favorite)
                }
            }
            listPlusFavorites.add(favoriteList)
            listPlusFavorites.addAll(state.articlesByWeeks)
            swipeRefreshLayout.isRefreshing = state.refreshing
            adapterByWeek.showArticles(listPlusFavorites)
        }
    }
}

private fun <E> MutableList<E>.add(element: Single<E>) {
    TODO("Not yet implemented")
}
