package com.monzo.androidtest.articles

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.monzo.androidtest.R
import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.common.parseDate

private var context: Context? = null

internal class ArticleAdapter(
    ctx: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var onItemClick: ((Article) -> Unit)? = null

    private val articles: MutableList<Article> = ArrayList()

    init {
        context = ctx
    }

    fun showArticles(articles: List<Article>){
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(context)
        val view = layoutInflater.inflate(R.layout.list_item_article, parent, false)
        return ArticleViewHolder(view)
    }


    override fun getItemCount(): Int {
        return articles.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val articleViewHolder = holder as ArticleViewHolder
        articleViewHolder.bind(articles[position])
        holder.itemView.setOnClickListener{
            articles[position].let {
                onItemClick?.invoke(it)
            }
        }
    }

    class ArticleViewHolder(view: View): RecyclerView.ViewHolder(view) {

        fun bind(article: Article){
            val thumbnail: ImageView = itemView.findViewById(R.id.article_thumbnail_imageview)
            val title: TextView = itemView.findViewById(R.id.article_headline_textview)
            val publishedDate: TextView = itemView.findViewById(R.id.article_date_textview)

            title.text = article.title
            publishedDate.text = parseDate(article.published)
            Glide.with(context!!).load(article.thumbnail).into(thumbnail)
        }
    }

}