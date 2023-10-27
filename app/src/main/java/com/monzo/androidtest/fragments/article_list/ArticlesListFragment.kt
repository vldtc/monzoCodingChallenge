package com.monzo.androidtest.fragments.article_list

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.monzo.androidtest.HeadlinesApp
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.ArticleByWeekAdapter
import com.monzo.androidtest.articles.ArticlesViewModel
import com.monzo.androidtest.articles.model.Article


class ArticlesListFragment : Fragment() {
    private lateinit var viewModel: ArticlesViewModel
    private lateinit var adapterByWeek: ArticleByWeekAdapter

    @SuppressLint("CheckResult")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_article_list, container, false)
        val swipeRefreshLayout = view.findViewById<SwipeRefreshLayout>(R.id.articles_swiperefreshlayout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.articles_by_week_recyclerview)

        viewModel = HeadlinesApp.from(requireActivity().applicationContext).inject(requireContext())
        adapterByWeek = ArticleByWeekAdapter(requireActivity())
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = adapterByWeek

        swipeRefreshLayout.setOnRefreshListener { viewModel.onRefresh() }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            val listPlusFavorites = mutableListOf<List<Article>>()
            val favoriteList = mutableListOf<Article>()
            if (state.favorites.isNotEmpty()) {
                state.favorites.forEach { article ->
                    viewModel.getFavorite(article.url)
                        .subscribe({ favoriteArticle ->
                            favoriteList.add(favoriteArticle)
                        }, { error ->
                            // Handle error if needed
                        })
                }
            }
                listPlusFavorites.add(favoriteList)
                listPlusFavorites.addAll(state.articlesByWeeks)
                swipeRefreshLayout.isRefreshing = state.refreshing
                adapterByWeek.showArticles(listPlusFavorites)
        }
        return view
    }
}