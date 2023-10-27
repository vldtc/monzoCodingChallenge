package com.monzo.androidtest.articles

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.common.getWeekNumber
import java.util.*



internal class ArticleByWeekAdapter(
        ctx: Context,
    //private val navController: NavController
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var context: Context? = null

    private val articles: MutableList<List<Article>> = ArrayList()

    init {
        context = ctx
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_articles_by_week, parent, false)
        return ArticleViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleViewHolder = holder as ArticleViewHolder
        articleViewHolder.bind(articles[position])


    }

    override fun getItemCount(): Int {
        return articles.size
    }

    fun showArticles(articles: List<List<Article>>) {
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    inner class ArticleViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val articleRecyclerView: RecyclerView = view.findViewById(R.id.articles_recyclerview)
        private val articleAdapter: ArticleAdapter = ArticleAdapter(itemView.context)
        private val weekNo: TextView = itemView.findViewById(R.id.articles_week_textview)
        init{
            articleRecyclerView.layoutManager = LinearLayoutManager(itemView.context)
            articleRecyclerView.adapter = articleAdapter
        }

        fun bind(articles: List<Article>) {
            articleAdapter.showArticles(articles)

            weekNo.text = getWeekNumber(articles[0].published)
            articleAdapter.onItemClick = {
                val bundle = Bundle().apply {
                    putString("ArticleUrl", it.url)
                }
                //navController.navigate(R.id.action_articlesListFragment_to_articleDetailsFragment, bundle)
            }
        }
    }
}
