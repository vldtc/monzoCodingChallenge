package com.monzo.androidtest.articles

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.monzo.androidtest.api.database.FavoritesDao
import com.monzo.androidtest.articles.model.Article
import com.monzo.androidtest.common.BaseViewModel
import com.monzo.androidtest.common.getStartOfWeek
import com.monzo.androidtest.common.plusAssign
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

@RequiresApi(Build.VERSION_CODES.N)
class ArticlesViewModel(
    private val repository: ArticlesRepository,
    private val favoritesDao: FavoritesDao
) : BaseViewModel<ArticlesState>(ArticlesState()) {

    private val favorites = favoritesDao.getFavorites()
    init {
        loadArticles()
        favorites.observeForever{ favorites ->
            setState { copy(favorites = favorites) }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun loadArticles() {
        disposables += repository.latestFintechArticles()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ articles ->
                setState {
                    copy(
                        refreshing = false,
                        articlesByWeeks = categorizeArticles(articles)
                    ) }
            }, { error ->
                setState {
                    copy(
                        refreshing = false
                    ) }
                Log.e("ArticlesViewModel", "Error loading articles", error)
            })
    }

    fun getFavorite(url: String): Single<Article> {
        return repository.getArticle(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    fun onRefresh() {
        setState {
            copy(
                refreshing = true,
                articlesByWeeks = emptyList()
            )}
        loadArticles()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun categorizeArticles(articles: List<Article>): List<List<Article>> {

        val articlesByWeeks = mutableListOf<List<Article>>()

        val groupedArticles = articles.groupBy { article ->
            getStartOfWeek(article.published)
        }

        val sortedKeys = groupedArticles.keys.sortedDescending()

        for (key in sortedKeys) {
            val weekArticles =
                groupedArticles[key]?.sortedByDescending { it.published } ?: emptyList()
            articlesByWeeks.add(weekArticles)
        }

        return articlesByWeeks

    }
}

data class ArticlesState(
    val refreshing: Boolean = true,
    val favorites: List<com.monzo.androidtest.api.database.Article> = emptyList(),
    var articlesByWeeks: List<List<Article>> = emptyList()

)
